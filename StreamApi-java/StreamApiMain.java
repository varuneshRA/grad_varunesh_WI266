import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

enum Gender { MALE, FEMALE }

class Emp {
    String name;
    int age;
    Gender gender;
    int salary;
    String designation;
    String department;

    Emp(String n, int a, int s, Gender g, String desig, String dept) {
        this.name = n;
        this.age = a;
        this.salary = s;
        this.gender = g;
        this.designation = desig;
        this.department = dept;
    }

    @Override
    public String toString() {
        return "Emp{name='" + name + "', age=" + age + ", salary=" + salary + 
               ", desig='" + designation + "', dept='" + department + "'}";
    }
}

public class StreamApiMain {
    public static void main(String[] args) {
        // Variable 'list' is declared here, inside main
        List<Emp> list = new ArrayList<>();


        // --- 20 DATA POINTS ADDED HERE ---
        list.add(new Emp("Karan", 24, 28000, Gender.MALE, "PROGRAMMER", "IT"));
        list.add(new Emp("Sanjana", 29, 33000, Gender.FEMALE, "PROGRAMMER", "IT"));
        list.add(new Emp("Rahul", 42, 54000, Gender.MALE, "MANAGER", "IT"));
        list.add(new Emp("Megha", 27, 31000, Gender.FEMALE, "CLERK", "HR"));
        list.add(new Emp("Varun", 35, 46000, Gender.MALE, "MANAGER", "HR"));
        list.add(new Emp("Priya", 30, 35000, Gender.FEMALE, "PROGRAMMER", "QA"));
        list.add(new Emp("Amit", 32, 38000, Gender.MALE, "CLERK", "QA"));
        list.add(new Emp("Sneha", 28, 32000, Gender.FEMALE, "CLERK", "QA"));
        list.add(new Emp("Rajesh", 45, 53000, Gender.MALE, "MANAGER", "SALES"));
        list.add(new Emp("Anjali", 23, 26000, Gender.FEMALE, "CLERK", "SALES"));
        list.add(new Emp("Kabir", 31, 37500, Gender.MALE, "PROGRAMMER", "MARKETING"));
        list.add(new Emp("Abhishek", 38, 49000, Gender.MALE, "MANAGER", "FINANCE"));
        list.add(new Emp("Tanya", 25, 27500, Gender.FEMALE, "CLERK", "FINANCE"));
        list.add(new Emp("Sunil", 50, 60000, Gender.MALE, "DIRECTOR", "ADMIN"));
        list.add(new Emp("Preeti", 40, 51000, Gender.FEMALE, "MANAGER", "ADMIN"));
        list.add(new Emp("Yash", 22, 24000, Gender.MALE, "CLERK", "CUSTOMER CARE"));
        list.add(new Emp("Nidhi", 26, 29000, Gender.FEMALE, "CLERK", "CUSTOMER CARE"));
        list.add(new Emp("Vijay", 48, 58000, Gender.MALE, "MANAGER", "OPERATIONS"));
        list.add(new Emp("Suresh", 55, 75000, Gender.MALE, "VP", "EXECUTIVE"));
        list.add(new Emp("Monica", 43, 52000, Gender.FEMALE, "MANAGER", "LOGISTICS"));

        // 1. Find the highest salary paid employee
        Emp highestSalaryEmp = list.stream()
                .max(Comparator.comparingInt(e -> e.salary))
                .orElse(null);
        System.out.println("Highest Salary: " + highestSalaryEmp);

        // 2. Find male & female employee counts
        Map<Gender, Long> genderCount = list.stream()
                .collect(Collectors.groupingBy(e -> e.gender, Collectors.counting()));
        System.out.println("Gender Count: " + genderCount);

        // 3. Total expense department wise
        Map<String, Integer> deptExpense = list.stream()
                .collect(Collectors.groupingBy(e -> e.department, 
                        Collectors.summingInt(e -> e.salary)));
        System.out.println("Dept Expenses: " + deptExpense);

        // 4. Top 5 senior employees
        List<Emp> seniorMost = list.stream()
                .sorted(Comparator.comparingInt((Emp e) -> e.age).reversed())
                .limit(5)
                .toList();
        System.out.println("Top 5 Seniors: " + seniorMost);

        // 5. Manager names only
        Predicate<Emp> isManager = e -> e.designation.equalsIgnoreCase("MANAGER");
        List<String> managers = list.stream()
                .filter(isManager)
                .map(e -> e.name)
                .toList();
        System.out.println("Managers: " + managers);

        // 6. Hike salary by 20% for non-managers
        list.stream()
                .filter(isManager.negate())
                .forEach(e -> e.salary += (int) (0.20 * e.salary));
        System.out.println("Hike applied to non-managers.");

        // 7. Total number of employees
        System.out.println("Total Employees: " + list.stream().count());
    } 
} 