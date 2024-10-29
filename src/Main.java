public class Main {
    public static void main(String[] args) {
        Store store = new Store();

        // создание отделов
        Department electronics = new Department("Electronics", "10 AM - 6 PM");
        Department groceries = new Department("Groceries", "8 AM - 10 PM");

        // добавление товаров в отделы
        electronics.addProduct(new Product("Laptop", 99999.99));
        electronics.addProduct(new Product("Smartphone", 45.999));

        groceries.addProduct(new Product("Milk", 69.99));
        groceries.addProduct(new Product("Bread", 45.99));

        // добавление отделов в магазин
        store.addDepartment(electronics);
        store.addDepartment(groceries);

        store.displayDepartments();
    }
}
