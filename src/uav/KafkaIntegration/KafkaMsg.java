package uav.KafkaIntegration;

public interface KafkaMsg {
    <T> void setValue(String valueName, T value);
    <T> T getValue(String valueName);
    String getStrValue(String valueName);
}
