package com.embedded.systems.carcontroller

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.embedded.systems.carcontroller.bluetooth.BluetoothService
import groovy.util.logging.Slf4j

@Slf4j
class MainActivity extends AppCompatActivity {

    private FrameLayout forward
    private FrameLayout backward
    private FrameLayout left
    private FrameLayout right
    private BluetoothService bluetoothService

    private final static int REQUEST_ENABLE_BT = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BluetoothAdapter blAdapter = BluetoothAdapter.defaultAdapter
        if (blAdapter == null) {
            log.warn "[!!!] Device does not support bluetooth"
            Toast.makeText(this, "Device does not support bluetooth", Toast.LENGTH_LONG).show()
            return
        }

        if (!blAdapter.enabled) {
            log.warn "[!!!] Bluetooth not enabled"
            Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show()
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        bluetoothService = new BluetoothService(blAdapter)
        bluetoothService.establishConnection({ boolean success ->

            if (success) {
                forward = findViewById(R.id.forward) as FrameLayout
                backward = findViewById(R.id.backward) as FrameLayout
                left = findViewById(R.id.left) as FrameLayout
                right = findViewById(R.id.right) as FrameLayout

                forward.onTouchListener = { View v, MotionEvent e ->
                    switch (e.action) {
                        case MotionEvent.ACTION_UP:
                            log.info("[!!!] F: UP")
                            break
                        case MotionEvent.ACTION_DOWN:
                            log.info("[!!!] F: DOWN")
                            break
                    }
                    false
                }
                forward.onClickListener = { View v -> }

                backward.onTouchListener = { View v, MotionEvent e ->
                    switch (e.action) {
                        case MotionEvent.ACTION_UP:
                            log.info("[!!!] B: UP")
                            break
                        case MotionEvent.ACTION_DOWN:
                            log.info("[!!!] B: DOWN")
                            break
                    }
                    false
                }
                backward.onClickListener = { View v -> }

                left.onTouchListener = { View v, MotionEvent e ->
                    switch (e.action) {
                        case MotionEvent.ACTION_UP:
                            log.info("[!!!] L: UP")
                            break
                        case MotionEvent.ACTION_DOWN:
                            log.info("[!!!] L: DOWN")
                            break
                    }
                    false
                }
                left.onClickListener = { View v -> }

                right.onTouchListener = { View v, MotionEvent e ->
                    switch (e.action) {
                        case MotionEvent.ACTION_UP:
                            log.info("[!!!] R: UP")
                            break
                        case MotionEvent.ACTION_DOWN:
                            log.info("[!!!] R: DOWN")
                            break
                    }
                    false
                }
                right.onClickListener = { View v -> }
            }
        })
    }

    void send(BluetoothSocket socket, Integer direction)
    {

    }
}
