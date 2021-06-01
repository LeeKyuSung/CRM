package com.example.crm;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

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

        //if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            System.out.println("[IncomingCallBroadcastReceiver][onReceive][EXTRA_STATE_RIGING]");

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (phoneNumber == null ) {
                Log.i("call-state", " : NULL");
            } else {
                Log.i("phone number : ", phoneNumber);
                if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                    Toast.makeText(context, "call Active", Toast.LENGTH_SHORT).show();
                } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                    Toast.makeText(context, "No call", Toast.LENGTH_SHORT).show();
                } else if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                    Toast.makeText(context, "Ringing State", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, phoneNumber, Toast.LENGTH_SHORT).show();
                }
            }
        //}
    }
}
