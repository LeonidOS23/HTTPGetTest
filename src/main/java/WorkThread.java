import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WorkThread implements Runnable {

    private int index;
    private Rec rec;

    public WorkThread(Rec record, int index) {
        this.rec = record;
        this.index = index;
    }

    @Override
    public void run() {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://" + this.rec.getURL());
            conn = (HttpURLConnection) url.openConnection();
            System.out.println("Processing record = " + this.index);
            rec.setStatus(conn.getResponseCode());
            System.out.println("Status of " + this.rec.getURL() + " is " + rec.getStatus());
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            rec.setProcessed(true);
        }
    }

    @Override
    public String toString() {
        return null;
    }

    public Rec returnRec() {
        return rec;
    }
}
