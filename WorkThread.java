import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            System.out.println("Status of current page is " + rec.getStatus());
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            conn.disconnect();
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
