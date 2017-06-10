package com.example.arduinosensors;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.provider.Settings.Secure;

public class MainActivity extends AppCompatActivity {
    private Button btnPOST, btnService, btnBlutoothActivity;
    private TextView tvID, tvQuartosMonitorizados;
    private ListView listview;
    private TinyDB tinyDB;
    private int globalPosition,
    lastAlertSize = 0,
    alertSize = 0;
    private String[] parts;
    private String response = "";
    private static String android_id;
    private AdapterView<?> globalparent;
    private AdapterAlertasPersonalizado adapter;
    protected static MyHandler hnd;
    private static boolean continueGet = true,
    serviceOnGoing = false,
    imediateAlert = false;
    private static ArrayList<String> quartosToSearch;
    private static ArrayList<String> quartoToSearchAndAlerts;
    private ArrayList<String> list = new ArrayList<>();
    private static ArrayList<AlertInfo> AlertOrderedInfo = new ArrayList<>();
    private int handleMessageCounter = 0;

    public static String getAndroid_id(){
        return android_id;
    }
    public static boolean  getContinueGet(){
        return continueGet;
    }
    public static boolean  getImediateAlert(){
        return imediateAlert;
    }
    public static void  setImediateAlert(boolean imediateAlertVar){
        imediateAlert=imediateAlertVar;
    }

    /**
     * setter for the all pendents alerts for the moment monitorizing rooms
     * @param newAlertOrderedInfo  pendents alerts to set
     */
    public static void setAlertOrderedInfo( ArrayList<AlertInfo> newAlertOrderedInfo){
        AlertOrderedInfo=newAlertOrderedInfo;
    }

    /**
     * setter for information of pendents alerts for the moment monitorizing rooms in string
     * @param newQuartoToSearchAndAlerts  pendents alerts to set
     */
    public static void setQuartoToSearchAndAlerts( ArrayList<String> newQuartoToSearchAndAlerts){
        quartoToSearchAndAlerts=newQuartoToSearchAndAlerts;
    }

    public static void  setServiceOnGoing(boolean serviceOnGoingVar){
        serviceOnGoing=serviceOnGoingVar;
    }
    public static ArrayList<String> getQuartosToSearch(){
        return quartosToSearch;
    }
    public static ArrayList<AlertInfo> getAlertOrderedInfo(){
        return AlertOrderedInfo;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bluetooth, menu);
        inflater.inflate(R.menu.menu_entry, menu);
        inflater.inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnMenuSettings:
                startActivityForResult(new Intent(MainActivity.this, MySettingsActivity.class), 0xe110);
                return true;

            case R.id.btnMenuBluetooth:
                startActivityForResult(new Intent(MainActivity.this, DeviceListActivity.class), 0xe110);
                return true;
            case R.id.btnMenuAbout:
                startActivityForResult(new Intent(MainActivity.this, AboutActivity.class), 0xe110);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void subOnCreate1 (){

    btnService.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            continueGet = true;
            Intent i = new Intent(MainActivity.this, BackgroundService.class);
            MainActivity.continueGet = true;
            startService(i);
        }
    });
    tinyDB = new TinyDB(getApplicationContext());
    //ListView part

    list.clear();
    for (int i = 0; i < AlertOrderedInfo.size(); ++i) {
        list.add("Room: " + AlertOrderedInfo.get(i).getQuarto() + "\nPaciente id: " + AlertOrderedInfo.get(i).getPaciente_ID() + "\nMachine: " + AlertOrderedInfo.get(i).getMaquina());
    }
    adapter = new AdapterAlertasPersonalizado(AlertOrderedInfo, this);


}
private void subOnCreate2(){
    listview.setAdapter(adapter);    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, final View view,
                                int position, long id) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);  alertDialogBuilder.setMessage("Solve Alert?"); globalPosition = position; globalparent = parent;
            alertDialogBuilder.setPositiveButton("yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {view.animate().setDuration(250).alpha(0).withEndAction(new Runnable() {
                                        @Override
                                        public void run() {if (globalPosition < list.size()) { // user may press while list is updated
                                                try {listview.setAdapter(adapter);
                                                    adapter.notifyDataSetChanged(); view.setAlpha(1); PostAlert postAlert = new PostAlert(AlertOrderedInfo.get(globalPosition)); Thread thr = new Thread(postAlert);thr.start();
                                                } catch (Exception e) {Toast.makeText(MainActivity.this, "Error solving alert", Toast.LENGTH_SHORT).show();} } } }); Toast.makeText(MainActivity.this, "Alert solved. Information sent to database.", Toast.LENGTH_SHORT).show(); }});
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "carregou", Toast.LENGTH_SHORT).show();}}); AlertDialog alertDialog = alertDialogBuilder.create(); alertDialog.show(); }

    });}


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tinyDB = new TinyDB(getApplicationContext());
        quartosToSearch = tinyDB.getListString("rooms");
        tvQuartosMonitorizados = (TextView) findViewById(R.id.tvQuartosMonitorizados);
        btnPOST = (Button) findViewById(R.id.buttonPOST);
        tvID = (TextView) findViewById(R.id.tvID);
        hnd = new MyHandler();
        listview = (ListView) findViewById(R.id.lv1);
        android_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        btnService = (Button) findViewById(R.id.btnService);
        subOnCreate1();
        subOnCreate2();
        updateVariablesOnBackButton();
        startActivityForResult(new Intent(MainActivity.this, myLoginActivity.class), 0xe111);}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0xe110)
            updateVariablesOnBackButton();
        if (requestCode == 0xe111) {
            continueGet = true;
            if (!serviceOnGoing) {
                Intent i = new Intent(MainActivity.this, BackgroundService.class);
                startService(i);
            }
        }
    }

    protected class MyHandler extends Handler {
        @Override
        public void handleMessage(Message m) {
            try {
                handleMessageCounter++;
                quartoToSearchAndAlerts = new ArrayList<>();
                String currentQuarto;
                String[] roomSplitter;
                response = m.getData().getString("alerts");
                lastAlertSize = alertSize;
                AlertOrderedInfo.clear();
                list.clear();
                alertSize = 0;
                roomSplitter = response.split(";");
                for (int roomIndex = 1; roomIndex < roomSplitter.length; roomIndex += 2) {
                    currentQuarto = roomSplitter[roomIndex];
                    parts = roomSplitter[roomIndex + 1].trim().split("!");
                    for (int i = 0; i < parts.length; ++i) {
                        if (!quartoToSearchAndAlerts.contains(currentQuarto))
                            quartoToSearchAndAlerts.add(currentQuarto);
                        String tempPaciente_ID = parts[i].split(",")[0];
                        String tempMaquina = parts[i].split(",")[1];
                        String tempAlertDate = parts[i].split(",")[2];
                        String tempMaquinaName = parts[i].split(",")[3];
                        list.add("Room: " + currentQuarto + "\nPacient id: " + tempPaciente_ID + "\nMachine: " + tempMaquinaName + "\nTime: " + tempAlertDate);
                        alertSize++;
                        AlertOrderedInfo.add(new AlertInfo(currentQuarto, tempPaciente_ID, tempMaquina, tempMaquinaName, tempAlertDate));
                    }
                }
                listview = (ListView) findViewById(R.id.lv1);
                adapter = new AdapterAlertasPersonalizado(AlertOrderedInfo, MainActivity.this);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                imediateAlert = alertSize > lastAlertSize;
            } catch (Exception e) {
                Log.d("Error handling Message","Error handling Message");
            }
        }
    }

    static String AlertOrderedInfoToString() {
        String result = "";
        for (int p = 0; p < AlertOrderedInfo.size(); p++) {
            result += (AlertOrderedInfo.get(p).toString());
        }
        return result;
    }

    static String quartoToSearchAndAlertsToString() {
        String result = "";
        for (int y = 0; y < quartoToSearchAndAlerts.size(); y++) {
            result += quartoToSearchAndAlerts.get(y) + ", ";
        }
        return result.substring(0, result.length() - 2) + ".";
    }

    protected class PostAlert implements Runnable {
        String numeroQuarto, numeroMaquina, Paciente_ID;

        PostAlert(AlertInfo infoToResolve) {
            this.numeroQuarto = infoToResolve.getQuarto();
            this.numeroMaquina = infoToResolve.getMaquina();
            this.Paciente_ID = infoToResolve.getPaciente_ID();

        }

        PostAlert(String numeroQuarto, String numeroMaquina, String Paciente_ID) {
            this.numeroQuarto = numeroQuarto;
            this.numeroMaquina = numeroMaquina;
            this.Paciente_ID = Paciente_ID;

        }

        @Override
        public void run() {

            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL("https://paginas.fe.up.pt/~up201507152/arduino/ChangeAlertState.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                urlConnection.setUseCaches(false);
                String payload = " maquina=" + numeroMaquina + "&Paciente_ID=" + Paciente_ID + "&Quarto=" + numeroQuarto + "&Nurse_ID=" + android_id + "&submit=Desligar+Alerta";
                DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                outputStream.writeBytes(payload);
                outputStream.flush();
                outputStream.close();

                int responseCode = urlConnection.getResponseCode();

            } catch (Exception e) {
                Log.d("CENAS", e.toString());
                showResult(e.toString());
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
        }
    }

    protected void showResult(final String response) {
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              try {
                                  tvID.setText(response);
                              } catch (Exception e) {
                                  tvID.setText("erro " + e.getMessage());
                              }
                          }
                      }
        );
    }

    public class AdapterAlertasPersonalizado extends BaseAdapter {
        private final List<AlertInfo> alertas;
        private final Activity act;

        public AdapterAlertasPersonalizado(List<AlertInfo> alertas, Activity act) {
            this.alertas = alertas;
            this.act = act;

        }

        @Override
        public int getCount() {
            return alertas.size();
        }

        @Override
        public Object getItem(int position) {
            return alertas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = act.getLayoutInflater()
                        .inflate(R.layout.listview_layout, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            AlertInfo alerta = alertas.get(position);

            //referências das Views
            TextView room = (TextView)
                    view.findViewById(R.id.lista_room);
            TextView machine = (TextView)
                    view.findViewById(R.id.lista_maquina);
            TextView time = (TextView)
                    view.findViewById(R.id.lista_time);
            ImageView image = (ImageView)
                    view.findViewById(R.id.lista_curso_personalizada_imagem);

            //populando as Views
            room.setText("Room: " + alerta.getQuarto());
            machine.setText("Machine: " + alerta.getMaquinaName());
            time.setText("Time: " + alerta.getAlertDate());
            image.setImageResource(R.drawable.alert_icon);
            return view;
        }
    }



    void updateVariablesOnBackButton() {
        try {
            quartosToSearch = tinyDB.getListString("rooms");
            String quartosMonitorizados = "Monitorizing Rooms: ";
            for (int u = 0; u < quartosToSearch.size(); u++) {
                quartosMonitorizados += quartosToSearch.get(u) + ", ";
            }
            tvQuartosMonitorizados.setText(quartosMonitorizados.substring(0, quartosMonitorizados.length() - 2));
            tvID.setText("Actual ID: " + android_id);
        } catch (Exception e) {
            tvID.setText("Update on back button error");
        }

    }

    private class ViewHolder {

        final TextView room, machine, time;
        final ImageView image;

        public ViewHolder(View view) {
            room = (TextView)view.findViewById(R.id.lista_room);
            machine = (TextView)view.findViewById(R.id.lista_maquina);
            time = (TextView)view.findViewById(R.id.lista_time);
            image = (ImageView)view.findViewById(R.id.lista_curso_personalizada_imagem);
        }

    }
}
