package uav.Document;

public enum DocState {
    CREATED("Created"),
    PENDING("Considered"),
    CONFIRMED("Signed"),
    FAILED("Archived");

    private String status;

    private DocState(final String status){
        this.status = status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    @Override
    public String toString(){
        return "DocState [status=" + this.status + "]";
    }
}
