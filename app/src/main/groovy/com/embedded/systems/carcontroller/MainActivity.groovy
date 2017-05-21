package com.embedded.systems.carcontroller

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.embedded.systems.carcontroller.bluetooth.Bluetooth
import com.embedded.systems.carcontroller.bluetooth.BluetoothService
import groovy.util.logging.Slf4j

@Slf4j
class MainActivity extends AppCompatActivity {

    private CardView forward
    private CardView backward
    private CardView left
    private CardView right
    private BluetoothService bluetoothService

    private Bluetooth bt

    private final static int REQUEST_ENABLE_BT = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        BluetoothAdapter blAdapter = BluetoothAdapter.defaultAdapter
//        if (blAdapter == null) {
//            log.warn "[!!!] Device does not support bluetooth"
//            Toast.makeText(this, "Device does not support bluetooth", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        if (!blAdapter.enabled) {
//            log.warn "[!!!] Bluetooth not enabled"
//            Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show()
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
//        }
//
//        bluetoothService = new BluetoothService(blAdapter)
//        bluetoothService.establishConnection({ boolean success ->
//
            forward = findViewById(R.id.forward) as CardView
            backward = findViewById(R.id.backward) as CardView
            left = findViewById(R.id.left) as CardView
            right = findViewById(R.id.right) as CardView

            forward.onTouchListener = { View v, MotionEvent e ->
                switch (e.action) {
                    case MotionEvent.ACTION_UP:
                        log.warn "[!!!] F: UP"
                        Buttons.forward = false
                        sendMessage()
                        break
                    case MotionEvent.ACTION_DOWN:
                        log.warn "[!!!] F: DOWN"
                        Buttons.forward = true
                        sendMessage()
                        break
                }
                false
            }
            forward.onClickListener = { View v -> }

            backward.onTouchListener = { View v, MotionEvent e ->
                switch (e.action) {
                    case MotionEvent.ACTION_UP:
                        log.warn "[!!!] B: UP"
                        Buttons.backward = false
                        sendMessage()
                        break
                    case MotionEvent.ACTION_DOWN:
                        log.warn "[!!!] B: DOWN"
                        Buttons.backward = true
                        sendMessage()
                        break
                }
                false
            }
            backward.onClickListener = { View v -> }

            left.onTouchListener = { View v, MotionEvent e ->
                switch (e.action) {
                    case MotionEvent.ACTION_UP:
                        log.warn "[!!!] L: UP"
                        Buttons.left = false
                        sendMessage()
                        break
                    case MotionEvent.ACTION_DOWN:
                        log.warn "[!!!] L: DOWN"
                        Buttons.left = true
                        sendMessage()
                        break
                }
                false
            }
            left.onClickListener = { View v -> }

            right.onTouchListener = { View v, MotionEvent e ->
                switch (e.action) {
                    case MotionEvent.ACTION_UP:
                        log.warn "[!!!] R: UP"
                        Buttons.right = false
                        sendMessage()
                        break
                    case MotionEvent.ACTION_DOWN:
                        log.warn "[!!!] R: DOWN"
                        Buttons.right = true
                        sendMessage()
                        break
                }
                false
            }
            right.onClickListener = { View v -> }
//        })

        bt = new Bluetooth(this, mHandler)
        connectService()
    }

    private void sendMessage()
    {
        if (Buttons.forward && !Buttons.backward && !Buttons.left && !Buttons.right)
            bt.sendMessage("1")
        else if (Buttons.forward && !Buttons.backward && !Buttons.left && Buttons.right)
            bt.sendMessage("2")
        else if (!Buttons.forward && !Buttons.backward && !Buttons.left && Buttons.right)
            bt.sendMessage("3")
        else if (!Buttons.forward && Buttons.backward && !Buttons.left && Buttons.right)
            bt.sendMessage("4")
        else if (!Buttons.forward && Buttons.backward && !Buttons.left && !Buttons.right)
            bt.sendMessage("5")
        else if (!Buttons.forward && Buttons.backward && Buttons.left && !Buttons.right)
            bt.sendMessage("6")
        else if (!Buttons.forward && !Buttons.backward && Buttons.left && !Buttons.right)
            bt.sendMessage("7")
        else if (Buttons.forward && !Buttons.backward && Buttons.left && !Buttons.right)
            bt.sendMessage("8")
        else if (!Buttons.forward && !Buttons.backward && !Buttons.left && !Buttons.right)
            bt.sendMessage("0")
    }

    void connectService(){
        try {
//            status.setText("Connecting...");
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.defaultAdapter
            if (bluetoothAdapter.enabled) {
                bt.start()
                bt.connectDevice("HC-06")
                log.debug "Btservice started - listening"
                Toast.makeText(this, "Bluetooth Connected", Toast.LENGTH_LONG).show()
//                status.setText("Connected");
            } else {
                log.debug "Btservice started - bluetooth is not enabled"
//                status.setText("Bluetooth Not enabled");
            }
        } catch(Exception e){
            log.debug "Unable to start bt ",e
//            status.setText("Unable to connect " +e);
        }
    }

    @Override
    boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    @Override
    boolean onOptionsItemSelected(MenuItem item) {
        switch (item.itemId) {
            case R.id.refresh :
                connectService()
                break
            default :
                return super.onOptionsItemSelected(item)
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:
                    log.debug "MESSAGE_STATE_CHANGE: " + msg.arg1
                    break
                case Bluetooth.MESSAGE_WRITE:
                    log.debug "MESSAGE_WRITE "
                    break
                case Bluetooth.MESSAGE_READ:
                    log.debug "MESSAGE_READ "
                    break
                case Bluetooth.MESSAGE_DEVICE_NAME:
                    log.debug "MESSAGE_DEVICE_NAME " + msg
                    break
                case Bluetooth.MESSAGE_TOAST:
                    log.debug "MESSAGE_TOAST " + msg
                    break
            }
        }
    }
}
