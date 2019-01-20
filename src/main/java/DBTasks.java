import java.sql.*;
import java.util.*;

public class DBTasks {
    public static List<Rec> collectLinks(String givenDate) throws ClassNotFoundException, SQLException{
        Locale.setDefault(Locale.ENGLISH);
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world",
                "root", "MyTest");
        String query = "SELECT URL FROM TEST WHERE CHECKDATE IS NULL OR CHECKDATE < ?";
        //WHERE ID < 100
        PreparedStatement prprdSttmnt = conn.prepareStatement(query);
        prprdSttmnt.setString(1, givenDate);
        List<Rec> rowValues = new ArrayList<>();
        ResultSet urlsList = prprdSttmnt.executeQuery();
        while (urlsList.next()) {
            Rec rec = new Rec(urlsList.getString(1), 0);
            rowValues.add(rec);
        }
        urlsList.close();
        prprdSttmnt.close();
        conn.close();
        System.out.println("Exported rows: " + rowValues.size());
        return rowValues;
    }

    public static void updateStatus(List<Rec> recList) throws SQLException {
        System.out.println("Importing " + recList.size() + " rows into database.");
        String query = "UPDATE TEST SET CHECKDATE = CURRENT_DATE, STATUS = ? WHERE URL = ?";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world",
                "root", "MyTest");
        PreparedStatement prprdSttmnt = conn.prepareStatement(query);
        final int limit = 2000;
        int count = 0;
        for (Rec r : recList) {
            int statuses = r.getStatus();
            String urls = r.getURL();
            prprdSttmnt.setInt(1, statuses);
            prprdSttmnt.setString(2, urls);
            prprdSttmnt.addBatch();
            if (++count % limit == 0) {
                prprdSttmnt.executeBatch();
            }
        }
        prprdSttmnt.executeBatch();
        prprdSttmnt.close();
        conn.close();
    }
}
