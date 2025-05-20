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

public class FireStoreConnection {
    private Firestore db;

    public FireStoreConnection() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("src/main/java/org/example/ecpe205-evangelio-firebase-adminsdk-fbsvc-361fb645da.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://ecpe205-evangelio-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        this.db = FirestoreClient.getFirestore();
    }

    public List<Object[]> getEmployeeTableRows() throws ExecutionException, InterruptedException {
        List<Object[]> rows = new ArrayList<>();

        ApiFuture<QuerySnapshot> query = db.collection("Employees").get();
        QuerySnapshot querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            Object[] row = new Object[] {
                    document.getString("id"),
                    document.getString("name"),
                    document.getString("position"),
                    document.getDouble("dailySalary"),
                    document.getDouble("daysPresent")
            };
            rows.add(row);
        }

        return rows;
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

    public void addEmployee(Employee employee, Payslip payslip) throws ExecutionException, InterruptedException {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", employee.getId());
        data.put("name", employee.getName());
        data.put("position", employee.getPosition());
        data.put("dailySalary", employee.getDailySalary());
        data.put("daysPresent", employee.getDaysPresent());
        data.put("grossPay", employee.computeGrossSalary());

        // Deductions
        data.put("pagIbig", payslip.getPagIbig());
        data.put("philHealth", payslip.getPhilHealth());
        data.put("sss",payslip.getSss());
        data.put("incomeTax", payslip.getIncomeTax());
        data.put("deductions", payslip.getTotalDeductions());

        // Net pay
        data.put("netPay", payslip.getNetPay());

        db.collection("Employees").document(employee.getId()).set(data).get();
    }
}
