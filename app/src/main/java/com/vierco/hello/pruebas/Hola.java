package com.vierco.hello.pruebas;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Hola extends Activity  {

    Button start;
    Button stop;
    Button comprobar;
    
    
    public boolean ComprobarServicioRunning(){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if ("com.vierco.hello.pruebas.Serv_Detector_balizas".equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hola);

        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        comprobar = (Button) findViewById(R.id.comprobar);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startService(new Intent(Hola.this, Serv_Detector_balizas.class));
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopService(new Intent(Hola.this,Serv_Detector_balizas.class));
            }
        });

        comprobar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(ComprobarServicioRunning()==true){
                    Toast.makeText(Hola.this,"Activiado",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Hola.this,"Desactivado",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
