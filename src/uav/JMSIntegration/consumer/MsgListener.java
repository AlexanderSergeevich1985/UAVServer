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
package uav.JMSIntegration.consumer;

import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.jms.IllegalStateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MsgListener implements MessageListener {
    private static Logger logger = Logger.getLogger(MsgListener.class.getName());

    private String consumerName;
    private QueueConnection qConnect = null;
    private QueueSession qSession = null;
    private Queue requestQ = null;
    private TransferQueue<Document> queue = new LinkedTransferQueue<>();

    public MsgListener(String consumerName) {
        this.consumerName = consumerName;
    }
    public void setupConnection(String cs, String requestQueue) {
        try {
            Context ctx = new InitialContext();
            QueueConnectionFactory qFactory = (QueueConnectionFactory) ctx.lookup(cs);
            qConnect = qFactory.createQueueConnection();
            qSession = qConnect.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            requestQ = (Queue)ctx.lookup(requestQueue);
            qConnect.start();
        }
        catch(JMSException jmse) {
            if(logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "JMSException occur : ", jmse);
            }
        }
        catch(NamingException nex) {
            if(logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "NamingException occur : ", nex);
            }
        }
    }
    public TransferQueue<Document> getQueue() {
        return this.queue;
    }
    public void onMessage(Message message) {
        if(message == null) return;
        try {
            if (message instanceof TextMessage) {
                onTextMsg((TextMessage) message);
            }
            else if (message instanceof StreamMessage) {
                onStreamMsg((StreamMessage) message);
            }
            else if (message instanceof BytesMessage) {
                onBytesMsg((BytesMessage) message);
            }
            else if (message instanceof MapMessage) {
                onMapMsg((MapMessage) message);
            }
            else if (message instanceof ObjectMessage) {
                onObjectMsg((ObjectMessage) message);
            }
            else {
                throw new IllegalStateException("Message Type Not Supported");
            }
        }
        catch(JMSException ex) {
            if(logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "JMSException occur : ", ex);
            }
        }
        catch(Exception ex) {
            if(logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "Exception occur : ", ex);
            }
        }
    }
    protected void onMapMsg(MapMessage msg) throws JMSException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchFieldException, InterruptedException {
        String class_name = (String) msg.getString("class_name");
        Class docClass = Class.forName(class_name);
        Document document = (Document) docClass.newInstance();
        document.setClass_name(class_name);
        Enumeration names = msg.getMapNames();
        while(names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if(name.equals("class_name")) continue;
            Field field = docClass.getField(name);
            field.setAccessible(true);
            field.set(document, msg.getObject(name));
        }
        queue.put(document);
    }
    protected void onTextMsg(TextMessage msg) throws JMSException {
    }
    protected void onStreamMsg(StreamMessage msg) throws JMSException {
    }
    protected void onBytesMsg(BytesMessage msg) throws JMSException {
    }
    protected void onObjectMsg(ObjectMessage msg) throws JMSException {
    }
}
