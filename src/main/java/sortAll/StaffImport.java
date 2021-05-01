package sortAll;

import java.util.ArrayList;
import java.util.List;

// Definition for Employee.
class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
}

public class StaffImport{

    int sum = 0;
    List<Employee> employees = null;
    public int getImportance(List<Employee> employees, int id) {
        this.employees = employees;
        this.sum = getImport(id);
        add(id);
        return sum;
    }
    public void add(int id){
        getsub(id).stream().forEach( x -> {
            System.out.println(x);
            sum += getImport(x);
            add(x);
        });
    }
    public List<Integer> getsub(int id){
        for (Employee x : this.employees) {
            if (x.id == id)
                return x.subordinates;
        }
        return null;
    }
    public int getImport(int id){
        for (Employee x : employees) {
            if (x.id == id)
                return x.importance;
        }
        return 0;
    }

    public static void main(String[] args) {
        StaffImport staffImport = new StaffImport();
        Employee employee = new Employee();
        Employee employee1 = new Employee();
        List<Integer> sub = List.of(2);
        employee.id = 1;
        employee.importance = 50;
        employee.subordinates = sub;
        employee1.id = 2;
        employee1.importance = 10;
        employee1.subordinates = new ArrayList<>();
        List<Employee> employees = List.of(employee1,employee);
        int res = staffImport.getImportance(employees, 1);
        System.out.println(res);
    }
}