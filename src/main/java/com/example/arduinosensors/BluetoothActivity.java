package com.example.arduinosensors;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;
import static android.animation.ObjectAnimator.ofPropertyValuesHolder;
import static android.animation.ValueAnimator.INFINITE;

/**
 * Class responsible for the user interface in real time
 */
public class BluetoothActivity extends Activity  {

   private TextView txtString;
   private  TextView txtStringLength;
    private static TextView oxygenLevel;
    private static TextView heartRate;
    private static TextView stressLabel;
    private static TextView tempTextView;
    private static TextView tvID;
    private static TextView attentionLabel;
    private static ImageView attentionImage;
    private static ProgressBar stressBar;
    private static ProgressBar oxygenBar;
    private GraphView graphic;
    private Graph graph;
    private ImageButton soundButton;
    private ImageView heartBeat;

    private Handler bluetoothIn;
    private static Handler graphIn;

    //HandlersIdentifiers
    private final int handlerState = 0;
    private static final int handlerState1 = 1;
    private static final int handlerState2 = 2;
    private static final int handlerState4 = 4;
    private final int handlerState5 = 5;

    private final int oxygenMaxValue=100;
    private final int stressMaxValue=100;
    private final int oxygenMinValue=0;
    private final int stressMinValue=0;
    private final int beatInitialDuration=0;
    private final float beatContraction= 0.5f;

    private static final int numberOfVariablesReceived=4;
    private static final int sixtyMillis= 60000;
    private final static int compensatoryTime = 20;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;

    private ConnectedThread mConnectedThread;
    private static ObjectAnimator heartBeatAnim;


    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address;

    private static boolean sound = true;

    public void startVariables(){
        setContentView(R.layout.activity_bluetooth);
        tvID  = (TextView) findViewById(R.id.tvID);
        txtString = (TextView) findViewById(R.id.txtString);
        txtStringLength = (TextView) findViewById(R.id.testView1);
        oxygenLevel = (TextView) findViewById(R.id.oxygenLevel);
        heartRate = (TextView) findViewById(R.id.heartRate);
        stressLabel = (TextView) findViewById(R.id.stressLabel);
        tempTextView = (TextView) findViewById(R.id.tempTextView);
        stressBar = (ProgressBar) findViewById(R.id.stressBar);
        oxygenBar = (ProgressBar) findViewById(R.id.oxygenBar);
        heartBeat = (ImageView) findViewById(R.id.heartBeat);
        graphic = (GraphView) findViewById(R.id.graph);
        soundButton = (ImageButton) findViewById(R.id.soundButton);
        attentionImage = (ImageView) findViewById(R.id.attentionImage);
        attentionLabel = (TextView) findViewById(R.id.attentionLabel);
    }

    public void setVariablesDefinitions(){
        attentionLabel.setBackgroundColor(Color.DKGRAY);
        tvID.setText("Actual ID: "+ MainActivity.getAndroid_id());
        graph= new Graph(graphic);
        graph.setInitialDefinitions();
        stressBar.setMax(stressMaxValue);
        stressBar.setProgress(stressMinValue);
        oxygenBar.setMax(oxygenMaxValue);
        oxygenBar.setProgress(oxygenMinValue);
        heartBeatAnim = ofPropertyValuesHolder(heartBeat,PropertyValuesHolder.ofFloat("scaleX", beatContraction), PropertyValuesHolder.ofFloat("scaleY", beatContraction));
        heartBeatAnim.setDuration(beatInitialDuration);
        heartBeatAnim.setRepeatCount(INFINITE);
        heartBeatAnim.setRepeatMode(ObjectAnimator.RESTART);
        heartBeatAnim.start();
    }

    /**
     * Handler to receive and process data from bluetooth device
     */
public void handler(){
    bluetoothIn = new Handler() {
        boolean tempSound = true;

        /**
         * handle message received from bluetooth
         * @param msg message received
         */
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case handlerState:
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    ReceivedData.setData(readMessage);
                    break;

                case handlerState1:
                    tempSound = Boolean.parseBoolean((String) msg.obj);
                    ReceivedData.setSound(tempSound);
                    break;

                case handlerState5:
                    if("ACK".equals ((String) msg.obj ))
                        ReceivedData.clearPPGwaveBuffer();
                    break;

            }
            ReceivedData.Process();
        }
    };

}

    /**
     * Set up onClick listener for change from normal to mute sound
     */
 public void buttonListener(){
     soundButton.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
             sound = !sound;
             bluetoothIn.obtainMessage(handlerState1, Boolean.toString(sound)).sendToTarget();
             if (sound == true) {
                 soundButton.setImageResource(R.drawable.normal_volume);
                 soundButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                 Toast.makeText(getBaseContext(), "Sound Activated", Toast.LENGTH_SHORT).show();
             } else {
                 soundButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                 soundButton.setImageResource(R.drawable.mute_volume);
                 Toast.makeText(getBaseContext(), "Mute", Toast.LENGTH_SHORT).show();
             }  }   });
 }
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startVariables();
        setVariablesDefinitions();
        handler();
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();
        buttonListener();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(mReceiver, filter);
    }
    /**
     * BroadcastReceiver to listen bluetooth intents
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action) && VerifyActivity.isActivityVisible()) {
                Toast.makeText(getBaseContext(), "Connection lost, please try again", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }
    };

    /**
     * Method to create a socket for bluetooth device
     * @param device MAC address
     * @return created Socket
     * @throws IOException
     */
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connection with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();
        VerifyActivity.activityResumed();

        /**
         * Thread to run the real time graph
         */
        new Thread(new Runnable() {
            boolean received = false;
            int sampleTime = 0;
            ArrayList<Double> ppgWaveBuffer = new ArrayList<Double>();

            @Override
            public void run() {
/**
 * Handler to receive data for graph input and make some acknowledges
 */
                graphIn = new Handler(Looper.getMainLooper()) {

                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case handlerState2:
                                received = Boolean.parseBoolean((String) msg.obj);
                                break;
                            case handlerState4:
                                sampleTime = Integer.parseInt((String) msg.obj);
                                break;
                            case handlerState1:
                                ppgWaveBuffer.addAll((ArrayList) msg.obj);
                                bluetoothIn.obtainMessage(handlerState5, "ACK").sendToTarget();
                                break;

                        }
                    }
                };

                for (; ; ) {

                    if (!VerifyActivity.isActivityVisible()) {
                        ppgWaveBuffer.clear();
                        break;
                    }
                    if (received == true && ppgWaveBuffer.size() != 0) {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (ppgWaveBuffer.size() != 0) {
                                    graph.update(ppgWaveBuffer);
                                }
                            }
                        });
                        try {
                            Thread.sleep(sampleTime + compensatoryTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        //Get MAC address from DeviceListActivity via intent
        final Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }

        // Establish the Bluetooth socket connection.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                Toast.makeText(getBaseContext(), "Connection lost, please try again", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //Send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause() {
        super.onPause();
        VerifyActivity.activityPaused();

        try {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            Toast.makeText(getBaseContext(), "Connection lost, please try again", Toast.LENGTH_LONG).show();
            onBackPressed();
        }

        boolean recieved1 = false;
        graphIn.obtainMessage(handlerState2, Boolean.toString(recieved1)).sendToTarget();

        graph.setPauseState();

        bluetoothIn.obtainMessage(handlerState, "#0,0,0,0~").sendToTarget();
        heartBeatAnim.setDuration(beatInitialDuration);
        attentionLabel.setVisibility(View.INVISIBLE);

    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off

    /**
     * Checks if the Android Bluetooth device  is available and prompts to be turned on if off
     */
    private void checkBTState() {

        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    /**
     * Class that extends thread responsible for receiving the
     * raw information and sends it to the specific handler
     */
    private class ConnectedThread extends Thread {
            private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        /**
         * Class Constructor
         * @param socket socket created for the specific connection
         */
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        /**
         * Method to listen for received messages
         */
        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method

        /**
         * Method to send data for the other bluetooth device
         * @param input message
         */
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    /**
     * Class responsible to process all data in the handler
     */
    public static class ReceivedData {

        private static ArrayList<Integer> cutPoints = new ArrayList<Integer>();
        private static ArrayList<Double> tempPPGWaveBuffer = new ArrayList<Double>();
        private static boolean sound = true;
        private static String oxygenValue="0";
        private static String heartRateValue="0";
        private static String stressValue="0";
        private static int tempSampleTime = 0;
        private static int integerbpm=0;
        private static StringBuilder data = new StringBuilder();
        private static boolean tempReceived = false;
        private static int endOfLineIndex = 0;

        /**
         * Getter for the last index of the line
         * @return last index of the line
         */
        public static int getEndOfLineIndex() {
            return endOfLineIndex;
        }

        /**
         * Getter for the cutpoints array (places with commas)
         * @return cutPoints
         */
        public static ArrayList<Integer> getCutPoints() {
            return cutPoints;
        }

        /**
         * Getter for PPG wave Buffer
         * @return PPG wave Buffer
         */
        public static ArrayList<Double> getTempPPGWaveBuffer() {

            return tempPPGWaveBuffer;
        }

        /**
         * Getter for if sound was activated
         * @return sound Boolean
         */
        public static boolean isSound() {
            return sound;
        }

        /**
         * Getter for oxygen value
         * @return oxygen value
         */
        public static String getOxygenValue() {
            return oxygenValue;
        }
        /**
         * Getter for heart rate value
         * @return heart rate value
         */
        public static String getHeartRateValue() {
            return heartRateValue;
        }

        /**
         * Getter for stress value
         * @return stress value
         */
        public static String getStressValue() {
            return stressValue;
        }
        /**
         * Getter for sample time value
         * @return sample time value
         */
        public static int getTempSampleTime() {
            return tempSampleTime;
        }
        /**
         * Getter for bpm value
         * @return bpm value
         */
        public static int getIntegerbpm() {
            return integerbpm;
        }
        /**
         * Getter for data string
         * @return data StringBuilder
         */
        public static StringBuilder getData() {
            return data;
        }
        /**
         * Getter for if data was received
         * @return received Boolean
         */
        public static boolean isTempReceived() {
            return tempReceived;
        }

        /**
         * Clear cutPoints and data
         */
        public static void clearData() {
            cutPoints.clear();
            data.delete(0, data.length());
        }

        /**
         * Setter for data StringBuilder
         * @param rowData string received
         */
        public static void setData(String rowData) {
            data.append(rowData);
        }

        /**
         * Checks if a string can be numeric
         * @param str input String
         * @return Verified condition
         */
        public static boolean isNumeric(String str)
        {
            return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
        }

        /**
         * Setter for activate or deactivate sound
         * @param tempSound Boolean option
         */
        public static void setSound(boolean tempSound) {
            sound = tempSound;
        }

        /**
         * Clean Buffer
         */
        public static void clearPPGwaveBuffer() {
            tempPPGWaveBuffer.clear();
        }

        /**
         * Process all the data from Graphic
         */
        public static void processGraphInformation () {

            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == ',')
                    cutPoints.add(i);
            }
            if(isNumeric(data.substring(1, cutPoints.get(0))))
                tempPPGWaveBuffer.add(Double.parseDouble(data.substring(1, cutPoints.get(0))));
            for (int j = 0; j < cutPoints.size(); j++) {

                if (j==cutPoints.size()-1){
                    if(isNumeric(data.substring(cutPoints.get(j)+1,endOfLineIndex )))
                        tempPPGWaveBuffer.add(Double.parseDouble(data.substring(cutPoints.get(j)+1,endOfLineIndex )));
                } else{
                    if(isNumeric(data.substring(cutPoints.get(j) + 1, cutPoints.get(j+1))))
                        tempPPGWaveBuffer.add(Double.parseDouble(data.substring(cutPoints.get(j) + 1, cutPoints.get(j+1))));
                }

            }
            graphIn.obtainMessage(handlerState4, Integer.toString(tempSampleTime)).sendToTarget(); //Comment this part to run junit tests
            if (tempSampleTime != 0) {
            tempReceived = true;
            graphIn.obtainMessage(handlerState2, Boolean.toString(tempReceived)).sendToTarget(); //Comment this part to run junit tests
            graphIn.obtainMessage(handlerState1, tempPPGWaveBuffer).sendToTarget(); //Comment this part to run junit tests
        } else {
            tempReceived = false;
            graphIn.obtainMessage(handlerState2, Boolean.toString(tempReceived)).sendToTarget(); //Comment this part to run junit tests
        }
    }

        /**
         * Process all data for labels
         */
        public static void processVariablesInformation () {

            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == ',')
                    cutPoints.add(i);
            }

            if (cutPoints.size() == numberOfVariablesReceived-1) {

                if(isNumeric(data.substring(1, cutPoints.get(0))))
                    oxygenValue = data.substring(1, cutPoints.get(0));

                if(isNumeric(data.substring(cutPoints.get(0) + 1, cutPoints.get(1))))
                    heartRateValue = data.substring(cutPoints.get(0) + 1, cutPoints.get(1));

                if(isNumeric(data.substring(cutPoints.get(1) + 1, cutPoints.get(2))))
                    stressValue = data.substring(cutPoints.get(1) + 1, cutPoints.get(2));

                if(isNumeric(data.substring(cutPoints.get(2) + 1, endOfLineIndex)))
                    tempSampleTime = Integer.parseInt(data.substring(cutPoints.get(2) + 1, endOfLineIndex));
            }
            if (Integer.parseInt(heartRateValue) > 0)
                integerbpm = sixtyMillis / Integer.parseInt(heartRateValue);
            else
                integerbpm = INFINITE;

            // Comment this part for junit tests
            if (integerbpm >= 0) {
                heartBeatAnim.setDuration(integerbpm);
                heartBeatAnim.start();
            }
            oxygenLevel.setText(oxygenValue + "%");
            heartRate.setText(" HR = " + heartRateValue + "BPM");
            stressLabel.setText(" Stress= " + stressValue + "%");
            stressBar.setProgress(Integer.parseInt(stressValue));
            oxygenBar.setProgress(Integer.parseInt(oxygenValue));

            if (Integer.parseInt(oxygenValue) < 95 || Integer.parseInt(heartRateValue) < 60 || Integer.parseInt(heartRateValue) > 120) {
                attentionImage.setVisibility(View.VISIBLE);
                attentionLabel.setVisibility(View.VISIBLE);

                if (sound == true) {
                    final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                    tg.startTone(ToneGenerator.TONE_CDMA_PIP);
                }
            } else {
                attentionImage.setVisibility(View.INVISIBLE);
                attentionLabel.setVisibility(View.INVISIBLE);
            }
            // until here
        }

        public static void Process() {
            endOfLineIndex = data.indexOf("~");                    // determine the end-of-line
            if (endOfLineIndex > 0) {                                           // make sure there data before ~

                switch (data.charAt(0)) {
                    case '#':
                        processVariablesInformation();
                        break;

                    case '*':
                        processGraphInformation();
                        break;
                }
                cutPoints.clear();
                data.delete(0, data.length());
            }
        }
    }


}

