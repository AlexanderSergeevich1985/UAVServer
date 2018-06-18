package uav.WebSocketIntegration.message;

import java.io.Serializable;

public class MsgIOTDevice implements Serializable {
    private MsgHeader header;
    private MsgBody body;

    MsgIOTDevice() {}
    MsgIOTDevice(MsgHeader header, MsgBody body) {
        this.header = header;
        this.body = body;
    }
    public void setHeader(MsgHeader header) {
        this.header = header;
    }
    public MsgHeader getHeader() {
        return this.header;
    }
    public void setBody(MsgBody body) {
        this.body = body;
    }
    public MsgBody getBody() {
        return this.body;
    }
}