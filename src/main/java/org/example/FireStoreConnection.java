package org.example;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class FireStoreConnection {
    private Firestore db;

    public FireStoreConnection() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("src/main/java/org/example/ecpe205-evangelio-firebase-adminsdk-fbsvc-5f87d0cd20.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://ecpe205-evangelio-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        this.db = FirestoreClient.getFirestore();
    }

    public void addEmployeeToFirestore(Employee employee, Payslip payslip) {
        CollectionReference employees = db.collection("Employees");
        try{
            if (employee == null || payslip == null) {
                throw new IllegalArgumentException("Employee and Payslip cannot be null");
            }
            Map<String, Object> employeeData = new HashMap<>();
            employeeData.put("Employee Id", employee.getId());
            employeeData.put("Employee Name", employee.getName());
            employeeData.put("Position", employee.getPosition());
            employeeData.put("Daily Salary", employee.getDailySalary());
            employeeData.put("Days Present", employee.getDaysPresent());
            employeeData.put("Days Absent", employee.getDaysAbsent());
            employeeData.put("Gross Salary", payslip.getGrossSalary());
            employeeData.put("SSS", payslip.getSss());
            employeeData.put("PhilHealth", payslip.getPhilHealth());
            employeeData.put("PagIbig", payslip.getPagIbig());
            employeeData.put("Income Tax", payslip.getIncomeTax());
            employeeData.put("NetPay", payslip.getNetPay());

            DocumentReference docRef = employees.document(employee.getId());
            ApiFuture<WriteResult> result = docRef.set(employeeData);

        } catch (Exception e) {
            throw new RuntimeException("Unexpected error saving employee", e);
        }
    }

    public List<Employee> getAllEmployeesFromFirestore() throws Exception {
        List<Employee> employees = new ArrayList<>();
        CollectionReference employeesCollection = db.collection("Employees");

        ApiFuture<QuerySnapshot> future = employeesCollection.get();
        QuerySnapshot querySnapshot = future.get();

        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            try{
                Map<String, Object> data = document.getData();
                if (data == null ||
                        !data.containsKey("Employee Id") || data.get("Employee Id") == null ||
                        !data.containsKey("Employee Name") || data.get("Employee Name") == null ||
                        !data.containsKey("Position") || data.get("Position") == null ||
                        !data.containsKey("Daily Salary") || data.get("Daily Salary") == null ||
                        !data.containsKey("Days Present") || data.get("Days Present") == null ||
                        !data.containsKey("Days Absent") || data.get("Days Absent") == null) {
                    System.out.println("Skipping incomplete employee document: " + document.getId()
                    );
                    continue;
                }
                    Employee employee = new Employee(
                    document.getString("Employee Id"),
                    document.getString("Employee Name"),
                    document.getString("Position"),
                    document.getDouble("Daily Salary"),
                    document.getDouble("Days Present"),
                    document.getDouble("Days Absent")
            );
             employees.add(employee);
            }catch (Exception e) {
                System.out.println("Error processing employee document " + document.getId() + ": " + e.getMessage());
            }
        }
        return employees;
    }


    public void deleteEmployeeFromFirestore(String employeeId) throws Exception {
        db.collection("Employees").document(employeeId).delete().get();
    }

    public void updateEmployeeInFirestore(Employee employee, Payslip payslip) throws Exception {
        DocumentReference docRef = db.collection("Employees").document(employee.getId());

        Map<String, Object> updates = new HashMap<>();

        updates.put("Employee Name", employee.getName());
        updates.put("Position", employee.getPosition());
        updates.put("Daily Salary", employee.getDailySalary());
        updates.put("Days Present", employee.getDaysPresent());
        updates.put("Days Absent", employee.getDaysAbsent());
        updates.put("Gross Salary", payslip.getGrossSalary());
        updates.put("SSS", payslip.getSss());
        updates.put("PhilHealth", payslip.getPhilHealth());
        updates.put("PagIbig", payslip.getPagIbig());
        updates.put("Income Tax", payslip.getIncomeTax());
        updates.put("NetPay", payslip.getNetPay());


        ApiFuture<WriteResult> result = docRef.update(updates);
        result.get();
    }
}
