package com.example.arduinosensors;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BackgroundService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private long lastMillis = System.currentTimeMillis();
    private long notificationTimeMillisOffset=45000;
    private  String response=null;
    private String tempResponse=null;
    private ArrayList<String> quartosToSearch;
    private TinyDB tinyDB;
    private Thread thrGETAlerts;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        MainActivity.setServiceOnGoing(true);
        tinyDB= new TinyDB(getApplicationContext());
       // get alerts Thread
         thrGETAlerts = new Thread(new Runnable() {
            @Override
            public void run() {
                while (MainActivity.getContinueGet()) {
                    quartosToSearch= tinyDB.getListString("rooms");
                    response="";
                    tempResponse="";
                        GetAlerts getAlert = new GetAlerts(quartosToSearch);
                        Thread thr = new Thread(getAlert);
                        thr.start();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thrGETAlerts.start();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        stopSelf();
    }


    protected class GetAlerts implements Runnable{
        ArrayList<String>  numeroQuarto;
        GetAlerts( ArrayList<String> quarto){
            this.numeroQuarto= quarto;
        }
        @Override
        public void run() {

            URL url;
            HttpURLConnection urlConnection= null;

            try{
                for( int i = 0; i < MainActivity.getQuartosToSearch().size(); i++) {
                    url = new URL("https://paginas.fe.up.pt/~up201507152/arduino/AlertTableSimple.php?Quarto=" + quartosToSearch.get(i));
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoInput(true);
                    urlConnection.setUseCaches(false);
                    Thread.sleep(25);
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == 200) {
                        tempResponse  = readStream(urlConnection.getInputStream());
                        if (tempResponse.length()>0)
                        response += ";"+quartosToSearch.get(i)+";"+tempResponse;
                    } else
                       createMessageAndSend("alerts", "get response error");
                }
                createMessageAndSend("alerts",response);
            } catch (Exception e) {
                createMessageAndSend("alerts", "1"+e.getMessage());
            }finally{
                if(urlConnection != null)
                    urlConnection.disconnect();
                if(((System.currentTimeMillis() - lastMillis> notificationTimeMillisOffset)
                        && (MainActivity.getAlertOrderedInfo().size()>0))
                        || (MainActivity.getImediateAlert())  ) {
                    pushNotification();
                    lastMillis= System.currentTimeMillis();
                    MainActivity.setImediateAlert(false);
                }
            }
        }
    }
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try{
            reader = new BufferedReader( new InputStreamReader(in));
            String line= "";
            while ((line= reader.readLine()) != null){
                response.append(line+" ");
            }
        } catch (IOException e) {
            return e.getMessage();
        }finally{
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        return response.toString();
    }
    private void createMessageAndSend(String key, String response){
    Message msg = MainActivity.hnd.obtainMessage();
    Bundle bnd = new Bundle();
    bnd.putString(key,response);
    msg.setData(bnd);
    MainActivity.hnd.sendMessage(msg);
}

    protected   void pushNotification() {
        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent showFullQuoteIntent = new Intent(getApplicationContext(), MainActivity.class);
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(BackgroundService.this, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notify = new Notification.Builder
                (getApplicationContext())
                .setContentTitle("title")
                .setContentText("Rooms: "+MainActivity.quartoToSearchAndAlertsToString())
                .setContentTitle("New pending alerts ("+ MainActivity.getAlertOrderedInfo().size()+")")
                .setSmallIcon(R.drawable.alert_icon)//action_bar_examples
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(MainActivity.AlertOrderedInfoToString()))
                .build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }

}
