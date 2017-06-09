package com.example.arduinosensors;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class DisplayMessageActivity extends AppCompatActivity {
    TextView tvOla;
    Button btnService;
    ListView lv;
    List GlobalList;

    ArrayAdapter<String> GlobalArrayAdapter;
    int globalPosition;
    AdapterView<?> globalparent;
    public static MyHandler hnd;

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message m) {
            int v = m.getData().getInt("count");
            tvOla.setText("Value = " + v);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
        tvOla = (TextView) findViewById(R.id.textView5);
        btnService = (Button) findViewById(R.id.btnService);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("Name", "");
        if (!name.equalsIgnoreCase("")) {
            name = name + "  Sethi";  /* Edit the value here*/
        }
        tvOla.setText(name);
        hnd = new MyHandler();
        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use this to start and trigger a service
                Intent i= new Intent(DisplayMessageActivity.this, BackgroundService.class);
                // potentially add data to the intent
                startService(i);
            }
        });

        // listView tests

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list );
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DisplayMessageActivity.this);
                alertDialogBuilder.setMessage("Solve Alert?");
                globalPosition = position;
                globalparent = parent;
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // list.remove(position);//or equalalent of remove method
                                // adapter.notifyDataSetChange();

                                final String item = (String) globalparent.getItemAtPosition(globalPosition);
                                view.animate().setDuration(500).alpha(0)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                list.remove(item);
                                                adapter.notifyDataSetChanged();
                                                view.setAlpha(1);
                                            }
                                        });
                                Toast.makeText(DisplayMessageActivity.this,"You clicked yes button",Toast.LENGTH_SHORT).show();

                            }

                        });
                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                /*final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(500).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });*/

            }

        });



    }



}

