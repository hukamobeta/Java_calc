import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class DBhandler {
    private static final String URL = "jdbc:mysql://localhost:3306/math_operations_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createOperationTable(String operationType) {
        String tableName = operationType.toLowerCase() + "_results";
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "operand1 DOUBLE, " +
                "operand2 DOUBLE, " +
                "result DOUBLE)";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблица " + tableName + " успешно создана.");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы " + tableName);
        }
    }
    public static void saveOperationResult(String operationType, double operand1, double operand2, double result) {
        String tableName = operationType.toLowerCase() + "_results";
        String sql = "INSERT INTO " + tableName + " (operand1, operand2, result) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, operand1);
            pstmt.setDouble(2, operand2);
            pstmt.setDouble(3, result);
            pstmt.executeUpdate();
            System.out.println("Результат операции сохранен в таблицу " + tableName);
        } catch (SQLException e) {
            System.out.println("Не удалось сохранить результат операции в таблицу " + tableName);
        }
    }

    // Метод для отображения всех таблиц
    public static void showAllTables() {
        // Получаем список всех таблиц
        String sqlTables = "SHOW TABLES";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rsTables = stmt.executeQuery(sqlTables)) {

            while (rsTables.next()) {
                String table = rsTables.getString(1);
                System.out.println("Таблица: " + table);

                String sqlData = "SELECT * FROM " + table;
                try (Statement stmtData = conn.createStatement();
                     ResultSet rsData = stmtData.executeQuery(sqlData)) {

                    ResultSetMetaData metaData = rsData.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(metaData.getColumnName(i) + "\t");
                    }
                    System.out.println();
                    while (rsData.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(rsData.getString(i) + "\t");
                        }
                        System.out.println();
                    }
                    System.out.println("-------------------------------------------------");
                } catch (SQLException e) {
                    System.out.println("Ошибка при получении данных из таблицы " + table);
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка таблиц");
        }
    }
}
