package kyusung.crm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.InputStream;

import kyusung.crm.pojo.Customer;
import kyusung.crm.pojo.Order;

public class CallingReceiver extends BroadcastReceiver {

    public static final String TAG = "PHONE STATE";
    private static String mLastState;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(TAG, "===================전화 수신========");
        Log.d(TAG,"onReceive()");
        System.out.println("[IncomingCallBroadcastReceiver][onReceive]");

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        /*if (state.equals(mLastState)) {
            return;

        } else {
            mLastState = state;
        }*/

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            System.out.println("전화 울리는 중");

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (phoneNumber == null ) {
                Log.i("call-state", " : NULL");
            } else {
                Log.i("phone number : ", phoneNumber);

                Customer target = null;
                boolean isFound = false;
                for (Customer customer : MainActivity.customers) {
                    if (customer.isTel(phoneNumber)) {
                        System.out.println("phone number Exist!!!!");
                        target = customer;
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
                    System.out.println("found customer id : " + target.getId());
                    showCustomer(context, target);
                } else {
                    System.out.println("phone number not found");
                }
            }
        }
    }

    public void showCustomer(final Context context, Customer customer) {

        // 주문 목록 검색
        String orderListString = "";

        System.out.println("showCustomer : " + customer.getId());
        String filename = "user" + customer.getId();
        String str = "";
        boolean haveData = true;
        try {
            InputStream fis = context.getResources().openRawResource(context.getResources().getIdentifier(filename,"raw", context.getPackageName()));
            byte[] data = new byte[fis.available()];
            while(fis.read(data)!=-1) {
                ;
            }
            str = new String(data);
        } catch (Exception e) {
            System.out.println("No order data");
            haveData = false;
        }
        if (haveData) {
            String[] inputList = str.split("\n");
            try {
                // TODO 최근 주문건 골라내는 로직 필요
                int startIndex = inputList.length-7;
                if (startIndex < 0)
                    startIndex = 0;
                for (int i=startIndex; i<inputList.length; i++) {
                    Order order = new Order(inputList[i]);
                    String menu = order.getMenu();
                    String price = order.getPrice();
                    String date = order.getDate();
                    if (!"".equals(menu)) {
                        orderListString += menu + " ";
                    }
                    if (!"".equals(price)) {
                        orderListString += price + " ";
                    }
                    if (!"".equals(date)) {
                        orderListString += "("+date +")";
                    }
                    orderListString += "\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 알림 띄우기
        String tmp = customer.getNotificationStr();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(tmp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(tmp + "\n" + orderListString));
                .setStyle(new NotificationCompat.BigTextStyle().bigText(orderListString));

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }
}
