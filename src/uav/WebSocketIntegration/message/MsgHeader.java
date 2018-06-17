package uav.WebSocketIntegration.message;

import java.io.Serializable;

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
        this.deviceUID = deviceUID;
    }
    public String getDeviceUID() {
        return this.deviceUID;
    }
}