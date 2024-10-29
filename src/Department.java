import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private String workingHours;
    private List<Product> products;

    public Department(String name, String workingHours) {
        this.name = name;
        this.workingHours = workingHours;
        this.products = new ArrayList<>();
    }

    // метод добавления товара
    public void addProduct(Product product) {
        products.add(product);
    }

    // метод удаления товара
    public void removeProduct(Product product) {
        products.remove(product);
    }

    // метод отображения товаров в отделе
    public void displayProducts() {
        System.out.println("Department: " + name + " (Working hours: " + workingHours + ")");
        for (Product product : products) {
            System.out.println(" - " + product);
        }
    }

    public String getName() {
        return name;
    }

    public String getWorkingHours() {
        return workingHours;
    }
}
