import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.sql.*;

public class ExcelExporter {
    public void export() {
        String sqlTables = "SHOW TABLES";
        try (Connection conn = DBhandler.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rsTables = stmt.executeQuery(sqlTables)) {
            while (rsTables.next()) {
                String table = rsTables.getString(1);
                String query = "SELECT * FROM " + table;
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(table);

                Row headerRow = sheet.createRow(0);
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    Cell cell = headerRow.createCell(i - 1);
                    cell.setCellValue(rsmd.getColumnName(i));
                }

                int rowIndex = 1;
                while (resultSet.next()) {
                    Row row = sheet.createRow(rowIndex++);

                    for (int i = 1; i <= columnCount; i++) {
                        Cell cell = row.createCell(i - 1);
                        cell.setCellValue(resultSet.getString(i));
                    }
                }

                try (FileOutputStream out = new FileOutputStream("/Users/maksimkukin/Java_finashka/Java_calc/result/" + table + ".xlsx")) {
                    workbook.write(out);
                }

                workbook.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
