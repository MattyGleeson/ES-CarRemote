package com.embedded.systems.carcontroller.bluetooth

import android.bluetooth.BluetoothSocket

/**
 * Created by matty on 28/04/2017.
 */
interface BluetoothEstablished {
    void callback (boolean success)
}

interface ReceiveSocket {
    void callback (BluetoothSocket socket)
}

interface RecieveOutputStream {
    void callback (BluetoothStreamsModel bluetoothStreamsModel)
}
