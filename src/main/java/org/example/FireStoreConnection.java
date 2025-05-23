package org.example;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class FireStoreConnection {
    private Firestore db;

    public FireStoreConnection() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("src/main/java/org/example/ecpe205-evangelio-firebase-adminsdk-fbsvc-55656005b8.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://ecpe205-evangelio-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        this.db = FirestoreClient.getFirestore();
    }

    public void addEmployeeToFirestore(Employee employee) {
        CollectionReference employees = db.collection("Employees");

        Map<String, Object> employeeData = new HashMap<>();
        employeeData.put("Id", employee.getId());
        employeeData.put("Name", employee.getName());
        employeeData.put("Position", employee.getPosition());
        employeeData.put("Daily Salary", employee.getDailySalary());
        employeeData.put("Days Present", employee.getDaysPresent());
        employeeData.put("Days Absent", employee.getDaysAbsent());

        DocumentReference docRef = employees.document(employee.getId());
        ApiFuture<WriteResult> result = docRef.set(employeeData);
    }

    public List<Employee> getAllEmployeesFromFirestore() throws Exception {
        List<Employee> employees = new ArrayList<>();
        CollectionReference employeesCollection = db.collection("Employees");

        ApiFuture<QuerySnapshot> future = employeesCollection.get();
        QuerySnapshot querySnapshot = future.get();

        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            Employee employee = new Employee(
                    document.getString("Id"),
                    document.getString("Name"),
                    document.getString("Position"),
                    document.getDouble("Daily Salary"),
                    document.getDouble("Days Present"),
                    document.getDouble("Days Absent")
            );
            employees.add(employee);
        }

        return employees;
    }

    public void deleteEmployeeFromFirestore(String employeeId) throws Exception {
        db.collection("Employees").document(employeeId).delete().get();
    }

    public void updateEmployeeInFirestore(Employee employee) throws Exception {
        DocumentReference docRef = db.collection("Employees").document(employee.getId());

        Map<String, Object> updates = new HashMap<>();
        updates.put("Name", employee.getName());
        updates.put("Position", employee.getPosition());
        updates.put("Daily Salary", employee.getDailySalary());
        updates.put("Days Present", employee.getDaysPresent());
        updates.put("Days Absent", employee.getDaysAbsent());

        ApiFuture<WriteResult> result = docRef.update(updates);
        result.get();
    }

    public List<Object[]> getPayrollRecords() throws ExecutionException, InterruptedException {
        List<Object[]> records = new ArrayList<>();

        ApiFuture<QuerySnapshot> query = db.collection("Employees").get();
        QuerySnapshot snapshot = query.get();

        for (DocumentSnapshot doc : snapshot.getDocuments()) {
            Object[] row = new Object[] {
                    doc.getString("id"),
                    doc.getString("name"),
                    doc.getString("position"),
                    doc.getDouble("dailySalary"),
                    doc.getDouble("daysPresent"),
                    doc.getDouble("grossPay"),
                    doc.getDouble("pagIbig"),
                    doc.getDouble("philHealth"),
                    doc.getDouble("sss"),
                    doc.getDouble("incomeTax"),
                    doc.getDouble("deductions"),
                    doc.getDouble("netPay")
            };
            records.add(row);
        }

        return records;
    }
}
