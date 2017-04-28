package com.embedded.systems.carcontroller.bluetooth

/**
 * Created by matty on 28/04/2017.
 */
class BluetoothStreamsModel {
    InputStream is
    OutputStream os

    BluetoothStreamsModel (InputStream is, OutputStream os)
    {
        this.is = is
        this.os = os
    }
}
