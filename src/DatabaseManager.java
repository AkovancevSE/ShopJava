import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC"); // Загружаем драйвер
            String url = "jdbc:sqlite:store.db"; // Путь к базе данных
            connection = DriverManager.getConnection(url);
            System.out.println("Подключение к базе данных установлено.");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер SQLite не найден: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }

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
                    department_id INTEGER NOT NULL,
                    FOREIGN KEY (department_id) REFERENCES departments (id)
                );
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createDepartmentsTable);
            stmt.execute(createProductsTable);
            System.out.println("Таблицы созданы или уже существуют.");
        } catch (SQLException e) {
            System.out.println("Ошибка при инициализации базы данных: " + e.getMessage());
        }
    }

    // сохранение отдела
    public void saveDepartment(String name, String workingHours) {
        String sql = "INSERT INTO departments (name, working_hours) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, workingHours);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении отдела: " + e.getMessage());
        }
    }

    // сохранение товара
    public void saveProduct(String name, double price, int departmentId) {
        String sql = "INSERT INTO products (name, price, department_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, departmentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    // отображение отделов и их товаров
    public void displayDepartmentsWithProducts() {
        String sqlDepartments = "SELECT * FROM departments";
        String sqlProducts = "SELECT * FROM products WHERE department_id = ?";

        try (Statement stmt = connection.createStatement();
             ResultSet departmentsRs = stmt.executeQuery(sqlDepartments)) {
            System.out.println("\n=== ОТДЕЛЫ ===");
            while (departmentsRs.next()) {
                int departmentId = departmentsRs.getInt("id");
                String name = departmentsRs.getString("name");
                String workingHours = departmentsRs.getString("working_hours");
                System.out.println("Отдел: " + name + " (Часы работы: " + workingHours + ")");

                try (PreparedStatement productStmt = connection.prepareStatement(sqlProducts)) {
                    productStmt.setInt(1, departmentId);
                    ResultSet productsRs = productStmt.executeQuery();

                    while (productsRs.next()) {
                        String productName = productsRs.getString("name");
                        double price = productsRs.getDouble("price");
                        System.out.println("  - Товар: " + productName + ", Цена: " + price);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при отображении отделов: " + e.getMessage());
        }
    }
}
