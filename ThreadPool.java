import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    public static void main(String[] args) throws ParseException, SQLException, ClassNotFoundException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String date = br.readLine();
        List<Rec> urls = DBTasks.collectLinks(date);
        ExecutorService executorService = Executors.newFixedThreadPool(2001);
        List<Rec> newRecords = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            WorkThread wt = new WorkThread(urls.get(i), i);
            newRecords.add(wt.returnRec());
            executorService.submit(wt);
        }
        boolean allprocessed = false;
        while (!allprocessed) {
            allprocessed = true;
            for (Rec record : newRecords) {
                if (!record.isProcessed()) {
                    allprocessed = false;
                    System.out.println(" ");
                    break;
                }
            }
        }
        executorService.shutdown();

        for (Rec record : newRecords) {
            System.out.println("status code = " + record.getStatus() + " for " + record.getURL());
        }
        DBTasks.updateStatus(newRecords);
        System.out.println("Finished all threads. newRecords size is " + newRecords.size());
    }
}
