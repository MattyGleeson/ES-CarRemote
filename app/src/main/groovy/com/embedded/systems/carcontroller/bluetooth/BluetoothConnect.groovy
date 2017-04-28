package com.embedded.systems.carcontroller.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import groovy.util.logging.Slf4j

/**
 * Created by matty on 28/04/2017.
 */
@Slf4j
class BluetoothConnect extends AsyncTask<Object, Void, BluetoothSocket> {

    private final BluetoothAdapter blAdapter
    private final ReceiveSocket listener
    private final UUID socketId

    BluetoothConnect(BluetoothAdapter blAdapter, ReceiveSocket listener)
    {
        this.blAdapter = blAdapter
        this.listener = listener

        this.socketId = UUID.randomUUID()
    }

    @Override
    protected BluetoothSocket doInBackground(Object... voids) {
        Set<BluetoothDevice> pairedDevices = blAdapter.bondedDevices
        BluetoothSocket tmp = null

        if (pairedDevices.size() > 0) {
//            // There are paired devices. Get the name and address of each paired device.
//            for (BluetoothDevice device : pairedDevices) {
//                String deviceName = device.name
//                String deviceHardwareAddress = device.address // MAC address
//            }

            BluetoothDevice device = pairedDevices.find { it.name == "HC-06" }

            try
            {
                tmp = device.createRfcommSocketToServiceRecord(socketId)
            }
            catch (IOException ex)
            {
                log.error "[!!!] Failed to create socket\n ${ex}"
            }

            blAdapter.cancelDiscovery()

            try
            {
                tmp.connect()
            }
            catch (IOException ex)
            {
                log.error "[!!!] Failed to connect socket\n ${ex}"

                try {
                    tmp.close()
                } catch (IOException closeException) {
                    log.error "[!!!] Failed to close the socket\n ${closeException}"
                }
                return null
            }

            return tmp
        }

        return null
    }

    @Override
    protected void onPostExecute(BluetoothSocket socket) {
        log.warn "[!!!] CONNECTION ESTABLISHED"

        listener.callback(socket)
    }
}
