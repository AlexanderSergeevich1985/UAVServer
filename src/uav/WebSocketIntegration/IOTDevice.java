package uav.WebSocketIntegration;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "IOTDEVICE")
public class IOTDevice {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "DEVICE_UID", unique = true, nullable = false)
    private String deviceUID;
    @Column(name = "DEVICE_TYPE")
    private String deviceType;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "coordinates")
    private Coordinates coordinates;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="PROPERTIES", joinColumns = @JoinColumn(name = "PROPERTY_ID"))
    @MapKeyColumn(name="PROPERTY_NAME")
    @MapKeyClass(String.class)
    @Column(name="PROPERTY")
    @BatchSize(size = 20)
    private Map<String, String> properties = new HashMap<>();

    public void setDeviceUID(String deviceUID) {
        this.deviceUID = deviceUID;
    }
    public String getDeviceUID() {
        return this.deviceUID;
    }
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public String getDeviceType() {
        return this.deviceType;
    }
    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }
    public String getProperty(String key) {
        return this.properties.get(key);
    }
    @Override
    public String toString() {
        return "Device [device UID=" + deviceUID + ", device Type=" + deviceType + "]";
    }
}