package com.embedded.systems.carcontroller.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import groovy.util.logging.Slf4j

/**
 * Created by matty on 28/04/2017.
 */

@Slf4j
class BluetoothService {

    private final BluetoothAdapter blAdapter
    private BluetoothSocket blSocket
    private BluetoothStreamsModel blStreamsModel

    BluetoothService (BluetoothAdapter blAdapter)
    {
        this.blAdapter = blAdapter
    }

    void establishConnection(BluetoothEstablished listener)
    {
        BluetoothConnect bct = new BluetoothConnect(blAdapter, { BluetoothSocket socket ->
            blSocket = socket
            listener.callback(socket ? true : false)
        })
        bct.execute()
    }

    void sendMessage (String msg)
    {
        if (!blStreamsModel || !blStreamsModel.os)
        {
            getStreams({ BluetoothStreamsModel bluetoothStreamsModel ->
                blStreamsModel = bluetoothStreamsModel
                send(msg)
            })
        }
        else
        {
            send(msg)
        }
    }

    private void send (String msg)
    {
        if (blStreamsModel.os && msg.length() > 0)
        {
            byte[] send = msg.bytes

            try
            {
                blStreamsModel.os.write(send)
            }
            catch (IOException ex)
            {
                log.error "[!!!] Failed to send message\n ${ex}"
            }
        }
        else
        {
            log.warn "[!!!] No output stream or empty message"
        }
    }

//    void sendMessage (Integer msg)
//    {
//        if (!blStreamsModel || !blStreamsModel.os)
//        {
//            getStreams({ BluetoothStreamsModel bluetoothStreamsModel ->
//                blStreamsModel = bluetoothStreamsModel
//                send(msg)
//            })
//        }
//        else
//        {
//            send(msg)
//        }
//    }
//
//    private void send (Integer msg)
//    {
//        if (blStreamsModel.os)
//        {
//
//        }
//    }

    private void getStreams (RecieveOutputStream listener)
    {
        BluetoothStreams bluetoothStreams = new BluetoothStreams(listener)
    }
}
