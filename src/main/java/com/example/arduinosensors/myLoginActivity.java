package com.example.arduinosensors;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class myLoginActivity extends AppCompatActivity {
    Button loginButton ;
    TextView tv4;
    EditText login_email, login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_my_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        loginButton = (Button) findViewById(R.id.btn_login);
        /*FloatingActionButton fab = (FloatingActionButton) findViewBy.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {Id(R
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        login_email = (EditText) findViewById(R.id.login_email);
        login_email.setText("LPOO");
        login_password = (EditText) findViewById(R.id.login_password);
        login_password.setText("Android2017");
        tv4 = (TextView) findViewById(R.id.textView4);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((login_email.getText().toString().length()>1) && (login_password.getText().toString().length()>1)) {
                    GetLogin getLogin = new GetLogin(login_email.getText().toString(), login_password.getText().toString());
                    Thread thr = new Thread(getLogin);
                    thr.start();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
               // Toast.makeText(myLoginActivity.this, "carregaste", Toast.LENGTH_LONG).show();
            }
        });


    }
    protected class GetLogin implements Runnable{
        String user, pass;
        String tempResponse= null;
        GetLogin(String user, String pass){
            this.user= user;
            this.pass= pass;
            //tv4.setText("User "+ user + " Password: " + pass.hashCode());
        }
        @Override
        public void run() {
            URL url;
            HttpURLConnection urlConnection= null;
            try{
                //url = new URL("https://paginas.fe.up.pt/~up201507152/arduino/AlertTableSimple.php?Quarto=3");
                //
                url = new URL("https://paginas.fe.up.pt/~up201507152/arduino/AlertTableSimple.php?User="+ user+ "&Password="+ pass.hashCode());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);
                Thread.sleep(25);
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    tempResponse  = readStream(urlConnection.getInputStream());
                    //  response += ";"+quart
                    // osToSearch.get(i)+";"+tempResponse;
                    if(tempResponse.contains("True"))
                    {
                        finish();
                    }
                    else
                    {
                        showResult("WrongCredentials");

                    }

                } else {
                    //createMessageAndSend("alerts", "get response error");
                    // break;
                    //showResult("erro");

                }

                //createMessageAndSend("alerts",response);
            } catch (Exception e) {
                //createMessageAndSend("alerts", "1"+e.getMessage());
                //showResult ( e.toString());
            }finally{
                if(urlConnection!=null)
                    urlConnection.disconnect();
            /*if(((System.currentTimeMillis() - lastMillis> notificationTimeMillisOffset)
                    && (MainActivity.AlertOrderedInfo.size()>0))
                    || (MainActivity.imediateAlert)  ) {
                pushNotification();
                lastMillis= System.currentTimeMillis();
                MainActivity.imediateAlert=false;
            }*/
                //if((MainActivity.AlertOrderedInfo.size() >(MainActivity.lastAlertOrderedInfo.size())) && (MainActivity.AlertOrderedInfo.size()>0)){


            }
        }

    }
    protected void showResult(final String response) {
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              try {
                                  if(response=="WrongCredentials")
                                  Toast.makeText(myLoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                              } catch (Exception e) {
                                  //tv4.setText("erro " + e.getMessage());
                              }
                          }
                      }
        );
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
    @Override
    public void onBackPressed() {
        Toast.makeText(myLoginActivity.this, "You must Login to move on", Toast.LENGTH_SHORT).show();
    }
}





