package com.vierco.hello.pruebas;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;

public class Serv_Detector_balizas extends Service implements BeaconConsumer {

    private static final String TAG = "dmode";
    private BeaconManager beaconManager;

    ArrayList<String> balizasYaComprobadas;

    Identifier id_UUIDproximity;
    Identifier id_major;
    Identifier id_minor;

    String UUIDproximity;
    String major;
    String minor;

    private boolean isRunning  = false;

    //region Métodos comunes del servicio
    public void conversionDedatosObtenidosDelBeacon(){

        UUIDproximity = id_UUIDproximity.toString();
        major = id_major.toString();
        minor = id_minor.toString();

        // He mandado ya esta baliza dentro de esta región al servidor?
        if (balizasYaComprobadas.contains(UUIDproximity)){
            //NO envío la baliza al servidor
            Log.i(TAG,"No envío la baliza al servidor");
        } else {
            //Envío la baliza al servidor y la añado al array de ya comprobadas
            Log.i(TAG,"Envío la baliza al servidor");
            balizasYaComprobadas.add(UUIDproximity);
            startActivity(new Intent(Serv_Detector_balizas.this, Hola.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }

        Log.i(TAG, "Datosdel beacon:"+"\n"+"proximityUuid:"+" "+ UUIDproximity+"\n"+
                "major:"+" "+major+"\n"+
                "minor:"+" "+minor);
    }
    //endregion

    //region Métodos del BeaconConsumer
    @Override
    public void onBeaconServiceConnect() {

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", Identifier.parse("96CB4FB7-65C3-463D-93C9-3D1923904EB3"), Identifier.parse("1"), null));

        } catch (RemoteException e) {
        }


        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                for (Beacon beacon : beacons) {
                    Log.i(TAG,"Beacon detected with id1: "+beacon.getId1()+" id2:"+beacon.getId2()+" id3: "+beacon.getId3()+" distance: "+beacon.getDistance());
                    
                    id_UUIDproximity = beacon.getId1();
                    id_major = beacon.getId2();
                    id_minor = beacon.getId3();

                    conversionDedatosObtenidosDelBeacon();

                }
            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }


        beaconManager.setMonitorNotifier(new MonitorNotifier() {

            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "Acabo de ver una baliza por primera vez!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "Ya no veo una baliza");
                //Borro todos lso campos del array de esta región
                balizasYaComprobadas.clear();
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "Acabo de conmutar entre ver/no ver balizas: " + state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
            Log.i(TAG, "Monitorizando… … … …");

        } catch (RemoteException e) {
        }
    }
    //endregion

    @Override
    public void onCreate() {
        Log.i(TAG, "Service BEACON onCreate");
        isRunning = true;

        balizasYaComprobadas = new ArrayList<String>();
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service BEACON onStartCommand");

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service BEACON onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;
        beaconManager.unbind(this);
        Log.i(TAG, "Service BEACON onDestroy");
    }
}
