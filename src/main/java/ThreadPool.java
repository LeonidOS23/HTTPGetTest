import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class ThreadPool {

    public static void main(String[] argv) throws ParseException, SQLException, ClassNotFoundException, IOException {
        System.out.println("Please input date in format \"yyyy.mm.dd\".");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String date = br.readLine();
        List<Rec> urls = DBTasks.collectLinks(date);
        ExecutorService executorService = Executors.newFixedThreadPool(1001);
        List<Rec> newRecords = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            WorkThread wt = new WorkThread(urls.get(i), i);
            executorService.submit(wt);
            newRecords.add(wt.returnRec());
        }
        boolean allProcessed = false;
        while (!allProcessed) {
            try {
                sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            allProcessed = true;
            for (Rec record : newRecords) {
                if (!record.isProcessed()) {
                    allProcessed = false;
                    break;
                }
            }
        }
        executorService.shutdown();
        if (newRecords.size() != 0) DBTasks.updateStatus(newRecords);
    }
}
