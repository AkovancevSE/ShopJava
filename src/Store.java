import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Department> departments;

    public Store() {
        this.departments = new ArrayList<>();
    }

    // метод добавления отдела
    public void addDepartment(Department department) {
        departments.add(department);
    }

    // метод отображения всех отделов
    public void displayDepartments() {
        System.out.println("Departments in the store:");
        for (Department department : departments) {
            System.out.println(" - " + department.getName());
            department.displayProducts();
        }
    }
}
