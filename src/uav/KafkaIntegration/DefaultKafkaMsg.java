package uav.KafkaIntegration;

public class DefaultKafkaMsg implements KafkaMsg {
    private String msgName;
    private Double msgValue;

    public DefaultKafkaMsg(String name, Double value) {
        this.msgName = name;
        this.msgValue = value;
    }
    public <T> void setValue(String valueName, T value) {
        switch(valueName) {
            case "msgName": {
                if(value instanceof String) this.msgName = (String) value;
                break;
            }
            case "msgValue": {
                if(value instanceof Double) this.msgValue = (Double) value;
                break;
            }
            default:
                break;
        }
    }
    public <T> T getValue(String valueName) {
        switch(valueName) {
            case "msgName": {
                return (T) this.msgName;
            }
            case "msgValue": {
                return (T) this.msgValue;
            }
            default:
                break;
        }
        return null;
    }
    public String getStrValue(String valueName) {
        switch(valueName) {
            case "msgName": {
                return this.msgName;
            }
            case "msgValue": {
                return String.valueOf(this.msgValue);
            }
            default:
                break;
        }
        return null;
    }
}
