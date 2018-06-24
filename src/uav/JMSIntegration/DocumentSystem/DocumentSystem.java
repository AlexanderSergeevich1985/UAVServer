/**The MIT License (MIT)
Copyright (c) 2018 by AleksanderSergeevich
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package uav.JMSIntegration.DocumentSystem;

import org.springframework.stereotype.Service;
import uav.JMSIntegration.Document.Document;
import uav.JMSIntegration.consumer.JMSSessionPool;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.Field;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DocumentSystem {
    private static Logger logger = Logger.getLogger(DocumentSystem.class.getName());

    private AtomicInteger threadCount = new AtomicInteger(0);
    private ConcurrentSkipListMap<String, JMSSessionPool> topics = new ConcurrentSkipListMap<>();
    private ConcurrentSkipListMap<Integer, PostPublisher> publishers = new ConcurrentSkipListMap<>();
    private ExecutorService executor = Executors.newCachedThreadPool();
    private InitialContext ctx;
    private ReentrantLock ctx_locker = new ReentrantLock();
    private ReadWriteLock arxive_locker = new ReentrantReadWriteLock();
    private Map<String, Document> arxive = new HashMap<>();

    public DocumentSystem() {
        try {
            ctx = new InitialContext();
        }
        catch(NamingException nex) {
            if(this.logger.isLoggable(Level.SEVERE)) {
                this.logger.log(Level.SEVERE, "NamingException occur : ", nex);
            }
        }
    }
    public void setCtx(InitialContext ctx) {
        this.ctx = ctx;
    }
    public Boolean createConToTopic(String topicName, String conFactName) {
        if(topics.containsKey(topicName)) return true;
        try {
            Topic topic = (Topic) this.ctx.lookup(topicName);
            TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.lookup(conFactName);
            TopicConnection topicConn = connFactory.createTopicConnection();
            JMSSessionPool sessionPool = new JMSSessionPool(topicConn);
            topicConn.setExceptionListener(sessionPool);
            sessionPool.setTopic(topic);
            sessionPool.setConnConsumer(topicConn.createConnectionConsumer(topic, null, sessionPool, 10));
            topics.put(topicName, sessionPool);
            topicConn.start();
        }
        catch(NamingException nex) {
            if(this.logger.isLoggable(Level.SEVERE)) {
                this.logger.log(Level.SEVERE, "NamingException occur : ", nex);
            }
            return false;
        }
        catch (JMSException jmex) {
            if(this.logger.isLoggable(Level.SEVERE)) {
                this.logger.log(Level.SEVERE, "JMSException occur : ", jmex);
            }
            return false;
        }
        return true;
    }
    public void addPostPublisher(String topicId) {
        if(!topics.containsKey(topicId)) return;
        PostPublisher postPublisher = new PostPublisher(threadCount.incrementAndGet());
        publishers.put(postPublisher.getThreadId(), postPublisher);
        try {
            ctx_locker.lock();
            TopicSession session = topics.get(topicId).getConnToTopic().createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            postPublisher.setSession(session, (Topic) ctx.lookup(topicId));
            ctx_locker.unlock();
        }
        catch(NamingException nex) {
            if(this.logger.isLoggable(Level.SEVERE)) {
                this.logger.log(Level.SEVERE, "NamingException occur : ", nex);
            }
        }
        catch (JMSException jmex) {
            if(this.logger.isLoggable(Level.SEVERE)) {
                this.logger.log(Level.SEVERE, "JMSException occur : ", jmex);
            }
        }
        executor.execute(postPublisher);
    }
    public boolean stopPostPublisher(Integer id) {
        if(!publishers.containsKey(id)) return false;
        publishers.get(id).stopThread();
        return true;
    }
    public void stopAllPostPublisher() {
        publishers.forEach((id, worker) -> {
            worker.stopThread();
            logger.log(Level.INFO, "Stop worker with id : ", id);
        });
        executor.shutdown();
    }
    public void arxiveDocument(String topicName, Document document) {
        arxive_locker.writeLock().lock();
        arxive.put(topicName, document);
        arxive_locker.writeLock().unlock();
    }
    
    public class PostPublisher implements Runnable {
        private Logger logger;
        private Integer threadId;
        private TransferQueue<Document> queue = new LinkedTransferQueue<>();
        private AtomicBoolean doWork = new AtomicBoolean(false);
        private TopicSession session;
        private TopicPublisher publisher;
        private Message msg;
        private ReentrantLock log_locker = new ReentrantLock();

        public PostPublisher(Integer threadId) {
            this.threadId = threadId;
            this.logger = Logger.getLogger(PostPublisher.class.getName().concat(String.valueOf(threadId)));
        }
        public void setThreadId(Integer threadId) {
            this.threadId = threadId;
        }
        public Integer getThreadId() {
            return this.threadId;
        }
        public void stopThread() {
            this.doWork.set(false);
            try {
                if (publisher != null) publisher.close();
            }
            catch(JMSException jmex) {
                log_locker.lock();
                if(this.logger.isLoggable(Level.SEVERE)) {
                    this.logger.log(Level.SEVERE, "JMSException occur : ", jmex);
                }
                log_locker.unlock();
            }
        }
        public void setSession(TopicSession session, Topic topic) {
            if(session == null) return;
            this.session = session;
            try {
                publisher = session.createPublisher(topic);
            }
            catch(JMSException jmex) {
                log_locker.lock();
                if(this.logger.isLoggable(Level.SEVERE)) {
                    this.logger.log(Level.SEVERE, "JMSException occur : ", jmex);
                }
                log_locker.unlock();
            }
        }
        public void setQueue(TransferQueue<Document> queue) {
            this.queue = queue;
        }
        public void setDocument(Document document) {
            if(document == null) return;
            if(queue == null) queue = new LinkedTransferQueue<>();
            try {
                queue.put(document);
            }
            catch(InterruptedException iex) {
                log_locker.lock();
                if(this.logger.isLoggable(Level.SEVERE)) {
                    this.logger.log(Level.SEVERE, "InterruptedException occur : ", iex);
                }
                log_locker.unlock();
            }
        }
        @Override
        public void run() {
            doWork.set(true);
            try {
                if (msg == null) session.createMessage();
            }
            catch(JMSException jex) {}
            while(doWork.get()) {
                try {
                    while(queue.isEmpty()) {
                        if (!doWork.get()) return;
                        Thread.sleep(100);
                    }
                    Document document = queue.poll();
                    Field[] fields = document.getClass().getFields();
                    for(int i = 0; i < fields.length; ++i) {
                        fields[i].setAccessible(true);
                        msg.setObjectProperty(fields[i].getName(), fields[i].get(document));
                    }
                    publisher.publish(msg);
                }
                catch(IllegalAccessException illaex) {
                    log_locker.lock();
                    if(this.logger.isLoggable(Level.SEVERE)) {
                        this.logger.log(Level.SEVERE, "IllegalAccessException occur : ", illaex);
                    }
                    log_locker.unlock();
                }
                catch(JMSException jmex) {
                    log_locker.lock();
                    if(this.logger.isLoggable(Level.SEVERE)) {
                        this.logger.log(Level.SEVERE, "JMSException occur : ", jmex);
                    }
                    log_locker.unlock();
                }
                catch(InterruptedException iex) {
                    log_locker.lock();
                    if(this.logger.isLoggable(Level.SEVERE)) {
                        this.logger.log(Level.SEVERE, "InterruptedException occur : ", iex);
                    }
                    log_locker.unlock();
                }
            }
        }
    }
}
