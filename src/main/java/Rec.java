public class Rec {

    private String urls;
    private int status;
    private boolean isProcessed;

    public Rec (String url, int statuses) {
        this.urls = url;
        this.status = statuses;
        this.isProcessed = false;
    }

    public boolean isProcessed(){
        return isProcessed;
    }

    public String getURL () {
        return urls;
    }

    public void setURL (String url) { this.urls = url; }

    public int getStatus () {
        return status;
    }

    public void setProcessed(boolean ch){
        this.isProcessed = ch;
    }

    public void setStatus (int i){
        this.status = i;
    }
}
