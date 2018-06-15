package uav.WebSocketIntegration;

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
    @Override
    public String toString() {
        return "Location [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", elevation=" + elevation + "]";
    }
}
