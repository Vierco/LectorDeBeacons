package com.vierco.hello.pruebas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class IniciarServicioEnBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, Serv_Detector_balizas.class);
            context.startService(serviceIntent);
        }
    }
}