package com.siae.siaecajamarca;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class broadcastservice extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            //Toast.makeText(context, "reinicio", Toast.LENGTH_SHORT).show();
            Intent service = new Intent(context.getApplicationContext(),  servicionoti.class);

            context.startService(service);

        }
    }
}
