public class Department {

    private int id;
    private String name;
    private Employee [] employees;
    private int employeeCount;
    // add your code here
    // there can be at most 20 departments
    private static Department [] departments = new Department[20];
    private static int deptcount = 0;

    // you are not allowed to write any other constructor
    public Department(int id, String name) {
        this.id = id;
        this.name = name;
        this.employees = new Employee[10];
        // add your code here
        departments[deptcount++] = this;
    }

    // add your code here
    public void addEmployee(Employee e){
        if(employeeCount==employees.length)return;
        employees[employeeCount++] = e;
    }

    public double getDepartmentSalary(){
        double ans = 0;
        for(int i = 0;i < employeeCount;i++)
            ans += employees[i].getSalary();
        return ans;
    }

    public Employee getMaxSalaryEmployee(){
        if(employeeCount==0)return null;
        Employee e = employees[0];
        for(int i = 1;i < employeeCount;i++)
        {
            if(e.getSalary()<employees[i].getSalary())
                e = employees[i];
        }
        return e;
    }

    public static double getTotalSalary(){
        double ans = 0;
        for(int i = 0; i < deptcount ; i++){
            ans += departments[i].getDepartmentSalary();
        }
        return ans;
    }
}