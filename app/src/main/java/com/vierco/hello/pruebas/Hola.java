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

//    protected static final String TAG = "dmode";
//    TextView mensaje;
//    private BeaconManager beaconManager;
//
//    Identifier id_UUIDproximity;
//    Identifier id_major;
//    Identifier id_minor;
//
//    String UUIDproximity;
//    String major;
//    String minor;
//
//    public void conversionDedatosObtenidosDelBeacon(){
//
//        UUIDproximity = id_UUIDproximity.toString();
//        major = id_major.toString();
//        minor = id_minor.toString();
//
//        Log.i(TAG, "Datosdel beacon:"+"\n"+"proximityUuid:"+" "+ UUIDproximity+"\n"+
//                                    "major:"+" "+major+"\n"+
//                                    "minor:"+" "+minor);
//    }

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

//        mensaje = (TextView) findViewById(R.id.mensaje);
//        beaconManager = BeaconManager.getInstanceForApplication(this);
//        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));
//        beaconManager.bind(this);

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

//    @Override
//    public void onBeaconServiceConnect() {
//
//        try {
//            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", Identifier.parse("96CB4FB7-65C3-463D-93C9-3D1923904EB3"), Identifier.parse("1"), null));
//
//        } catch (RemoteException e) {
//        }
//
////        beaconManager.setRangeNotifier(new RangeNotifier() {
////            @Override
////            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
////                if (beacons.size() > 0) {
////                    Log.i(TAG, "El primer beacon que veo está: " + beacons.iterator().next().getDistance() + " meters away.");
////
////
////                    Log.i(TAG, "Voy leyendo datos:"+"\n"+"proximityUuid:"+" "+ beacons.iterator().next().getId1()+"\n"+
////                                    "major:"+" "+beacons.iterator().next().getId2()+"\n"+
////                                    "minor:"+" "+beacons.iterator().next().getId3());}
////
////            }
////        });
//
////        beaconManager.setRangeNotifier(new RangeNotifier() {
////            @Override
////            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
////                for (Beacon beacon : beacons) {
////                    Log.i(TAG,"Beacon detected with id1: "+beacon.getId1()+" id2:"+beacon.getId2()+" id3: "+beacon.getId3()+" distance: "+beacon.getDistance());
////                }
////            }
////
////        });
//
//                beaconManager.setRangeNotifier(new RangeNotifier() {
//            @Override
//            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
//                for (Beacon beacon : beacons) {
////                    Log.i(TAG,"Beacon detected with id1: "+beacon.getId1()+" id2:"+beacon.getId2()+" id3: "+beacon.getId3()+" distance: "+beacon.getDistance());
//
//                    id_UUIDproximity = beacon.getId1();
//                    id_major = beacon.getId2();
//                    id_minor = beacon.getId3();
//
//                    conversionDedatosObtenidosDelBeacon();
//
//
//                }
//            }
//
//        });
//
//        try {
//            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
//        } catch (RemoteException e) {
//        }
//
//
//        beaconManager.setMonitorNotifier(new MonitorNotifier() {
//
//            @Override
//            public void didEnterRegion(Region region) {
//                Log.i(TAG, "Acabo de ver una baliza por primera vez!");
//            }
//
//            @Override
//            public void didExitRegion(Region region) {
//                Log.i(TAG, "Ya no veo una baliza");
//            }
//
//            @Override
//            public void didDetermineStateForRegion(int state, Region region) {
//                Log.i(TAG, "Acabo de conmutar entre ver/no ver balizas: " + state);
//            }
//        });
//
//        try {
//            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
//            Log.i(TAG, "Monitorizando… … … …");
//
//        } catch (RemoteException e) {
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        beaconManager.unbind(this);
//    }

}