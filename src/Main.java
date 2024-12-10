public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();

        dbManager.connect();

        dbManager.initializeDatabase();

        // добавление данных в базу
        dbManager.saveDepartment("Electronics", "10 AM - 6 PM");
        dbManager.saveDepartment("Groceries", "8 AM - 10 PM");

        dbManager.saveProduct("Laptop", 99999.99, 1); // department_id = 1
        dbManager.saveProduct("Smartphone", 45999.99, 1);
        dbManager.saveProduct("Milk", 69.99, 2); // department_id = 2
        dbManager.saveProduct("Bread", 45.99, 2);

        dbManager.disconnect();
    }
}
