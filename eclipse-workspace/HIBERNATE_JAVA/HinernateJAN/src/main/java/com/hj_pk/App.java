package com.hj_pk;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class App {

    public static void main(String[] args) {
        // Register a shutdown hook to close the SessionFactory
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Hibernate...");
            Util.shutdown();
        }));

        // Perform CRUD operations
        createEmployee("John Doe", "IT", 70000);
        createEmployee("Jane Smith", "HR", 50000);

        System.out.println("\n--- All Employees ---");
        List<Employee> employees = getAllEmployees();
        employees.forEach(System.out::println);

        System.out.println("\n--- Update Employee ---");
        updateEmployee(1, "John Updated", "Engineering", 80000);

        System.out.println("\n--- Employee After Update ---");
        Employee updatedEmployee = getEmployeeById(1);
        System.out.println(updatedEmployee);

        System.out.println("\n--- Delete Employee ---");
        deleteEmployee(2);

        System.out.println("\n--- Employees After Deletion ---");
        employees = getAllEmployees();
        employees.forEach(System.out::println);
    }

    // Create (Insert) an Employee
    public static void createEmployee(String name, String department, double salary) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = new Employee(name, department, salary);
            session.persist(employee);
            transaction.commit();
            System.out.println("Employee Created: " + employee);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Read (Retrieve) all Employees
    public static List<Employee> getAllEmployees() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        }
    }

    // Read (Retrieve) an Employee by ID
    public static Employee getEmployeeById(int id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        }
    }

    // Update an Employee
    @SuppressWarnings("deprecation")
	public static void updateEmployee(int id, String name, String department, double salary) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                employee.setName(name);
                employee.setDepartment(department);
                employee.setSalary(salary);
                session.update(employee);
                transaction.commit();
                System.out.println("Employee Updated: " + employee);
            } else {
                System.out.println("Employee not found with ID: " + id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Delete an Employee
    public static void deleteEmployee(int id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.remove(employee);
                transaction.commit();
                System.out.println("Employee Deleted: " + employee);
            } else {
                System.out.println("Employee not found with ID: " + id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
