import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

public class DBTasks {
    public static List<Rec> collectLinks(String givenDate) throws ClassNotFoundException, SQLException, ParseException {
        Locale.setDefault(Locale.ENGLISH);
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world",
                "root", "MyTest");
        String query = "SELECT URL FROM TEST WHERE CHECKDATE < CONVERT(?, DATE)";
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
        System.out.println("exported" + rowValues.size());
        return rowValues;
    }

    public static void updateStatus(List<Rec> recList) throws SQLException {
        String query = "UPDATE TEST SET CHECKDATE = CURRENT_DATE, STATUS = ? WHERE URL = ?";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world",
                "root", "MyTest");
        PreparedStatement prprdSttmnt = conn.prepareStatement(query);
        final int limit = 2000;
        int count = 0;
        int i = 0;
        System.out.println("preparing update, strings " + recList.size());
        for (Rec r : recList) {
            int statuses = r.getStatus();
            String urls = r.getURL();
            prprdSttmnt.setInt(1, statuses);
            prprdSttmnt.setString(2, urls);
            prprdSttmnt.addBatch();
            i++;
            System.out.println("SQL cycle " + i);
            if (++count % limit == 0) {
                System.out.println("importing batch");
                prprdSttmnt.executeBatch();
            }
        }
        prprdSttmnt.executeBatch();
        System.out.println("importing leftovers");
        prprdSttmnt.close();
        conn.close();
    }
}
