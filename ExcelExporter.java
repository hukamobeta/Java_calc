import java.sql.*;

public class ExcelExporter {
    public void export() {
        String sqlTables = "SHOW TABLES";
        try (Connection conn = DBhandler.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rsTables = stmt.executeQuery(sqlTables)) {

            while (rsTables.next()) {
                String table = rsTables.getString(1);
                String query = "SELECT * FROM " + table + " INTO OUTFILE '/Users/maksimkukin/Java_finashka/Java_calc/result/" + table + ".csv'";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.executeQuery();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
