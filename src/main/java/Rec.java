public class Rec {

    private String urls;
    private int status;
    private boolean isProcessed;

    public Rec (String url, int statuses) {
        this.urls = url;
        this.status = statuses;
        this.isProcessed = false;
    }

    public void setProcessed(){
        this.isProcessed = true;
    }

    public boolean isProcessed(){ return this.isProcessed; }

    public String getURL () {
        return urls;
    }

    public int getStatus () {
        return status;
    }

    public void setStatus (int i){
        this.status = i;
    }
}
