package uav.WebSocketIntegration.message;

import java.io.Serializable;
import java.util.UUID;

public class MsgHeader implements Serializable {
    private String messageId;
    private String messageType;
    private String timestamp;
    private long bodySize;
    private String deviceUID;

    public MsgHeader() {}
    public MsgHeader(String messageId, String messageType, String timestamp, Long bodySize) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.timestamp = timestamp;
        this.bodySize = bodySize;
    }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getMessageId() { return this.messageId; }
    public void setMessageType(String messageType) { this.messageType = messageType; }
    public String getMessageType() { return this.messageType; }
    public void setTimeStamp(String timestamp) { this.timestamp = timestamp; }
    public String getTimeStamp() { return this.timestamp; }
    public void setBodySize(long bodySize) { this.bodySize = bodySize; }
    public long getBodySize() { return this.bodySize; }
    public void setDeviceUID(String deviceUID) {
        if(!deviceUID.isEmpty())
            this.deviceUID = deviceUID;
        else
            this.deviceUID = UUID.randomUUID().toString();
    }
    public String getDeviceUID() {
        return this.deviceUID;
    }
    @Override
    public String toString() {
        return "Message header [id=" + messageId + ", type=" + messageType + ", timestamp=" + timestamp +
                ", body size=" + bodySize + ", device UUID" + deviceUID +"]";
    }
}
