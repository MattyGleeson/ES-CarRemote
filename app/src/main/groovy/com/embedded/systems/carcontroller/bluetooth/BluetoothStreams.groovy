package com.embedded.systems.carcontroller.bluetooth

import android.os.AsyncTask

/**
 * Created by matty on 28/04/2017.
 */
class BluetoothStreams extends AsyncTask<Object, Void, BluetoothStreamsModel> {

    RecieveOutputStream listener

    BluetoothStreams (RecieveOutputStream listener)
    {
        this.listener = listener
    }

    @Override
    protected BluetoothStreamsModel doInBackground(Object... objects) {
        return new BluetoothStreamsModel(null, null)
    }

    @Override
    protected void onPostExecute(BluetoothStreamsModel bluetoothStreamsModel) {
        listener.callback(bluetoothStreamsModel)
    }
}
