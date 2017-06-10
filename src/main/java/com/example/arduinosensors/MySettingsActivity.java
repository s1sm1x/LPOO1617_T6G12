package com.example.arduinosensors;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;



public class MySettingsActivity extends Activity {
    private ListView evRooms;
    private TextView tvID;
    private EditText edAddRoom;
    private Button btnOkAddRoom;
    private ArrayAdapter adapter;
    private int globalPosition;
    private AdapterView<?> globalparent;
    private TinyDB tinyDB;
    private ArrayList<String> quartosToSearch;

    private void subOnCreate1(){
        evRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MySettingsActivity.this); alertDialogBuilder.setMessage("Remove room?");globalPosition = position;globalparent = parent;
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {final String item = (String) globalparent.getItemAtPosition(globalPosition); view.animate().setDuration(500).alpha(0).withEndAction(new Runnable() {
                                @Override
                                public void run() { quartosToSearch.remove(item); evRooms.setAdapter(adapter); adapter.notifyDataSetChanged(); view.setAlpha(1); tinyDB.putListString("rooms", quartosToSearch);}});Toast.makeText(MySettingsActivity.this, "Room removed", Toast.LENGTH_SHORT).show();}});
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}});
                AlertDialog alertDialog = alertDialogBuilder.create(); alertDialog.show();}

        });
        btnOkAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {if (!quartosToSearch.contains(edAddRoom.getText().toString())) { quartosToSearch.add(edAddRoom.getText().toString()); Collections.sort(quartosToSearch);  tinyDB.putListString("rooms", quartosToSearch);adapter.notifyDataSetChanged();}edAddRoom.setText("");}
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); evRooms = (ListView) findViewById(R.id.evRooms);edAddRoom = (EditText) findViewById(R.id.edAddRoom); btnOkAddRoom = (Button) findViewById(R.id.btnOkAddRoom); tvID = (TextView) findViewById(R.id.tvID);
        tinyDB = new TinyDB(getApplicationContext()); quartosToSearch = tinyDB.getListString("rooms");
        if(tinyDB.getString("Nurse_ID").length()>0) tvID.setText("ID Actual: \n"+ MainActivity.getAndroid_id()) ; else  tvID.setText("ID Actual: (Sem id)");
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, quartosToSearch);
        evRooms.setAdapter(adapter);
        subOnCreate1();
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
