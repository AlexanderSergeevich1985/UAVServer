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

import javax.persistence.*;

@Entity
@Table(name = "COORDINATES")
public class Coordinates {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "LATITUDE")
    private Double latitude;
    @Column(name = "LONGITUDE")
    private Double longitude;
    @Column(name = "ELEVATION")
    private Double elevation;
    @OneToOne(mappedBy="coordinates")
    private IOTDevice iotDevice;

    public Coordinates() {}
    public Coordinates(Integer id, Double latitude, Double longitude, Double elevation) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return this.id;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getLongitude() {
        return this.longitude;
    }
    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }
    public Double getElevation() {
        return this.elevation;
    }
    public void setIOTDevice(IOTDevice iotDevice) {
        this.iotDevice = iotDevice;
    }
    public IOTDevice getIOTDevice() {
        return this.iotDevice;
    }
    @Override
    public String toString() {
        return "Location [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", elevation=" + elevation + "]";
    }
}
