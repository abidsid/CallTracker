package com.abid.admin.calltracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


import java.lang.reflect.Method;
import java.util.Set;

public class MyBroadcastReceiver extends BroadcastReceiver {

    SharedPreferences prefs;
    @Override
    public void onReceive(Context context, Intent intent) {
            prefs = context.getSharedPreferences("SHARED_PREFS_FILE", Context.MODE_PRIVATE);

            Set<String> numSet = prefs.getStringSet("NumberList",null);

            String SpamNumber = "+12539501212";

            System.out.println("Receiver start");
            Toast.makeText(context," Receiver start ", Toast.LENGTH_SHORT).show();
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            Toast.makeText(context,""+state,Toast.LENGTH_SHORT).show();
            Toast.makeText(context,""+number,Toast.LENGTH_SHORT).show();

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Class c = Class.forName(tm.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                Object telephonyService =m.invoke(tm);
                Bundle bundle = intent.getExtras();

                //  String phoneNumber = bundle.getString("incoming_number");
                // Log.e("INCOMING", phoneNumber);

                Method m2 = telephonyService.getClass().getDeclaredMethod("silenceRinger");
                Method m3 = telephonyService.getClass().getDeclaredMethod("endCall");


                if ((SpamNumber != null) && SpamNumber.equalsIgnoreCase(number)) {

                    Log.e("Spam ", SpamNumber);
                    Toast.makeText(context,"This is a Spam Call from "+number,Toast.LENGTH_SHORT).show();
                }

                else if((numSet != null) && numSet.contains(number)) {
                   // telephonyService.silenceRinger();
                    //telephonyService.endCall();
                    m2.invoke(telephonyService);
                    m3.invoke(telephonyService);
                    Log.e("HANG UP", number);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}
