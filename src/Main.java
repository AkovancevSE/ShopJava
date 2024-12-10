import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.connect();
        dbManager.initializeDatabase();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Добавить отдел");
            System.out.println("2. Добавить товар");
            System.out.println("3. Показать все отделы");
            System.out.println("4. Выйти");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Введите название отдела: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите часы работы отдела: ");
                    String workingHours = scanner.nextLine();
                    dbManager.saveDepartment(name, workingHours);
                    System.out.println("Добавлен отдел: " + name);
                }
                case 2 -> {
                    System.out.print("Введите название товара: ");
                    String productName = scanner.nextLine();
                    System.out.print("Введите цену товара: ");
                    double price = scanner.nextDouble();
                    System.out.print("Введите ID отдела, к которому относится товар: ");
                    int departmentId = scanner.nextInt();
                    scanner.nextLine();
                    dbManager.saveProduct(productName, price, departmentId);
                    System.out.println("Добавлен товар: " + productName);
                }
                case 3 -> dbManager.displayDepartmentsWithProducts();
                case 4 -> {
                    System.out.println("Завершение работы...");
                    running = false;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }

        dbManager.disconnect();
        scanner.close();
    }
}
