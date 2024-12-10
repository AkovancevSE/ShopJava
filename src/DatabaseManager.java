import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;

    // метод для подключения к базе данных
    public void connect() {
        try {
            String url = "jdbc:sqlite:store.db"; // Укажите путь к вашей базе данных
            connection = DriverManager.getConnection(url);
            System.out.println("Подключение к базе данных установлено.");
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }

    // метод для закрытия подключения
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Подключение к базе данных закрыто.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии подключения: " + e.getMessage());
        }
    }

    // метод для создания таблиц
    public void initializeDatabase() {
        String createDepartmentsTable = """
            CREATE TABLE IF NOT EXISTS departments (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                working_hours TEXT NOT NULL
            );
        """;

        String createProductsTable = """
            CREATE TABLE IF NOT EXISTS products (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                department_id INTEGER,
                FOREIGN KEY (department_id) REFERENCES departments(id)
            );
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createDepartmentsTable);
            stmt.execute(createProductsTable);
            System.out.println("Таблицы созданы или уже существуют.");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблиц: " + e.getMessage());
        }
    }

    // метод для добавления отдела
    public void saveDepartment(String name, String workingHours) {
        String sql = "INSERT INTO departments (name, working_hours) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, workingHours);
            pstmt.executeUpdate();
            System.out.println("Добавлен отдел: " + name);
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении отдела: " + e.getMessage());
        }
    }

    // метод для добавления товара
    public void saveProduct(String name, double price, int departmentId) {
        String sql = "INSERT INTO products (name, price, department_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, departmentId);
            pstmt.executeUpdate();
            System.out.println("Добавлен товар: " + name);
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении товара: " + e.getMessage());
        }
    }
}
