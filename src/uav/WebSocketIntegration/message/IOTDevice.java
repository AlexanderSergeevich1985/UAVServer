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
package uav.WebSocketIntegration.message;

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
