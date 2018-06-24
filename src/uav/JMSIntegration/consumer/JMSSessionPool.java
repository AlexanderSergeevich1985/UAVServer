package uav.JMSIntegration.consumer;

import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JMSSessionPool implements ServerSessionPool, ExceptionListener {
    private static Logger logger = Logger.getLogger(JMSSessionPool.class.getName());

    private final TopicConnection connToTopic;
    private String topicName = "";
    private Topic topic;
    private ConnectionConsumer connConsumer;

    private AtomicInteger sessionCount = new AtomicInteger(0);
    private ConcurrentSkipListMap<Integer, JMSSession> sessions = new ConcurrentSkipListMap<>();

    private ReentrantLock exec_locker = new ReentrantLock();
    private ExecutorService executor = Executors.newCachedThreadPool();

    public JMSSessionPool(TopicConnection connToTopic) {
        this.connToTopic = connToTopic;
    }
    public TopicConnection getConnToTopic() {
        return this.connToTopic;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    public String getTopicName() {
        return this.topicName;
    }
    public void setConnConsumer(ConnectionConsumer connConsumer) {
        this.connConsumer = connConsumer;
    }
    public void setTopic(Topic topic) {
        this.topic = topic;
    }
    public ServerSession createNewServerSession() {
        Integer sessionId = sessionCount.incrementAndGet();
        JMSSession session = new JMSSession(this.connToTopic, this.topicName, sessionId);
        this.sessions.put(sessionId, session);
        return session;
    }
    public synchronized ServerSession getServerSession() {
        return createNewServerSession();
    }
    public void onException(JMSException jmex) {
        if(this.logger.isLoggable(Level.SEVERE)) {
            this.logger.log(Level.SEVERE, "NamingException occur : ", jmex);
        }
    }
    public void stopAll() {
        sessions.forEach((id, listener) -> {
            listener.stop();
            logger.log(Level.INFO, "Stop listener with id : ", id);
        });
        executor.shutdown();
    }

    public class JMSSession implements ServerSession {
        private Logger logger;
        private TopicSession topicSession;

        JMSSession(TopicConnection connToTopic, String topicId, Integer sessionId) {
            this.logger = Logger.getLogger(JMSSession.class.getName().concat("_").concat(String.valueOf(sessionId)));
            try {
                this.topicSession = connToTopic.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
                MsgListener listener = new MsgListener(topicId);
                topicSession.setMessageListener(listener);
            }
            catch(JMSException jmex) {
                if(this.logger.isLoggable(Level.SEVERE)) {
                    this.logger.log(Level.SEVERE, "JMSException occur : ", jmex);
                }
            }
        }
        public synchronized Session getSession() throws JMSException {
            return this.topicSession;
        }
        public void start() throws JMSException {
            exec_locker.lock();
            executor.execute(topicSession);
            exec_locker.unlock();
        }
        public void stop() {
            try {
                if (topicSession != null) topicSession.close();
            }
            catch(JMSException jmex) {
                if(this.logger.isLoggable(Level.SEVERE)) {
                    this.logger.log(Level.SEVERE, "JMSException occur : ", jmex);
                }
            }
        }
    }
}
