package com.embedded.systems.carcontroller

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import groovy.util.logging.Slf4j

@Slf4j
class MainActivity extends Activity {

    private FrameLayout forward
    private FrameLayout backward
    private FrameLayout left
    private FrameLayout right

    private final static int REQUEST_ENABLE_BT = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forward = findViewById(R.id.forward) as FrameLayout
        backward = findViewById(R.id.backward) as FrameLayout
        left = findViewById(R.id.left) as FrameLayout
        right = findViewById(R.id.right) as FrameLayout

        forward.onTouchListener = { View v, MotionEvent e ->
            switch (e.action) {
                case MotionEvent.ACTION_UP :
                    log.info("[!!!] F: UP")
                    break
                case MotionEvent.ACTION_DOWN :
                    log.info("[!!!] F: DOWN")
                    break
            }
            false
        }
        forward.onClickListener = { View v -> }

        backward.onTouchListener = { View v, MotionEvent e ->
            switch (e.action) {
                case MotionEvent.ACTION_UP :
                    log.info("[!!!] B: UP")
                    break
                case MotionEvent.ACTION_DOWN :
                    log.info("[!!!] B: DOWN")
                    break
            }
            false
        }
        backward.onClickListener = { View v -> }

        left.onTouchListener = { View v, MotionEvent e ->
            switch (e.action) {
                case MotionEvent.ACTION_UP :
                    log.info("[!!!] L: UP")
                    break
                case MotionEvent.ACTION_DOWN :
                    log.info("[!!!] L: DOWN")
                    break
            }
            false
        }
        left.onClickListener = { View v -> }

        right.onTouchListener = { View v, MotionEvent e ->
            switch (e.action) {
                case MotionEvent.ACTION_UP :
                    log.info("[!!!] R: UP")
                    break
                case MotionEvent.ACTION_DOWN :
                    log.info("[!!!] R: DOWN")
                    break
            }
            false
        }
        right.onClickListener = { View v -> }
    }

    void send()
    {
        BluetoothAdapter blAdapter = BluetoothAdapter.defaultAdapter
        if (blAdapter == null) {
            // Device does not support Bluetooth
            return
        }

        if (!blAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        Set<BluetoothDevice> pairedDevices = blAdapter.bondedDevices

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.name
                String deviceHardwareAddress = device.address // MAC address
            }

        }
    }
}
