package uav.JMSIntegration.DocumentSystem;

import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import uav.JMSIntegration.Document.Document;
import uav.JMSIntegration.consumer.MsgListener;

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
    private ConcurrentSkipListMap<String, MsgListener> topics = new ConcurrentSkipListMap<>();
    private ConcurrentSkipListMap<Integer, PostPublisher> publishers = new ConcurrentSkipListMap<>();
    private ConcurrentSkipListMap<String, TopicConnection> connections = new ConcurrentSkipListMap<>();
    private ExecutorService executor = Executors.newCachedThreadPool();
    private InitialContext ctx;
    private ReentrantLock ctx_locker = new ReentrantLock();

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
    public void registerListener(MsgListener listener) {
        topics.putIfAbsent(listener.getConsumerName(), listener);
    }
    public void addPostPublisher(String topicId) {
        if(!connections.containsKey(topicId)) return;
        PostPublisher postPublisher = new PostPublisher(threadCount.incrementAndGet());
        publishers.put(postPublisher.getThreadId(), postPublisher);
        try {
            ctx_locker.lock();
            TopicSession session = connections.get(topicId).createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            postPublisher.setSession(session, (Topic) ctx.lookup("topic/".concat(topicId)));
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
