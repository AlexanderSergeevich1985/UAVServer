package uav.GridComputation;

public class RouterResponse {
    private String endpointIp;
    private Integer endpointPort;

    public void setEndpointIp(String endpointIp) {
        this.endpointIp = endpointIp;
    }
    public String getEndpointIp() {
        return this.endpointIp;
    }
    public void setEndpointPort(Integer endpointPort) {
        this.endpointPort = endpointPort;
    }
    public Integer getEndpointPort() {
        return this.endpointPort;
    }
    @Override
    public String toString() {
        return "Endpoint parameters [ip=" + endpointIp + ", port=" + endpointPort + "]";
    }
}
