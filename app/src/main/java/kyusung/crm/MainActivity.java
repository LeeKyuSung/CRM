package kyusung.crm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.InputStream;
import java.util.HashSet;

import kyusung.crm.pojo.Customer;


public class MainActivity extends AppCompatActivity {

    private static final String location = "raw/customers";
    public static HashSet<Customer> customers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO 검색 창 만들기

        requestPermission();

        // 파일 읽어오기
        getCustomers();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_PHONE_STATE},1);
        }
    }

    private void getCustomers() {
        customers = new HashSet<>();

        String str = "";
        try {
            InputStream fis = getResources().openRawResource(R.raw.customers);
            byte[] data = new byte[fis.available()];
            while(fis.read(data)!=-1) {
                ;
            }
            str = new String(data);
        } catch (Exception e) {
            str = "failed read";
            e.printStackTrace();
        }

        System.out.println("==============================asdfasdfafds");
        String[] inputList = str.split("\n");
        try {
            for (int i=0; i<inputList.length; i++) {
                Customer customer = new Customer(inputList[i]);
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}