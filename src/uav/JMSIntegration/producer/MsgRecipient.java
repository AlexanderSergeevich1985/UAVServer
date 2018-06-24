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
package uav.JMSIntegration.producer;

import uav.JMSIntegration.Document.Document;
import uav.JMSIntegration.DocumentSystem.DocumentSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TransferQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract public class MsgRecipient {
    private static Logger logger = Logger.getLogger(MsgRecipient.class.getName());

    private String inTopicName;
    private TransferQueue<Document> inQueue;
    private DocumentSystem documentSystem;
    private Map<String, Integer> directory = new HashMap<>();

    public void setInQueue(TransferQueue<Document> inQueue) {
        this.inQueue = inQueue;
    }
    public void setDocumentSystem(DocumentSystem documentSystem) {
        this.documentSystem = documentSystem;
    }
    public void checkTopic() {
        try {
            while(!this.inQueue.isEmpty()) {
                Document document = this.inQueue.take();
                if(processDoc(document)) {
                    documentSystem.arxiveDocument(inTopicName, document);
                }
                else {
                    documentSystem.sendDocument(directory.get(""), document);
                }
            }
        }
        catch(InterruptedException ex) {
            if(logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Exception occur : ", ex);
            }
        }
    }
    abstract public Boolean processDoc(Document document);
}
