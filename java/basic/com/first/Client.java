package basic.com.first;

/**
 * Created by $rohit on 11/16/2015.
 */

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import javax.net.SocketFactory;

public class Client extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    public Socket socket ;
    String response = "";
    boolean send = false ;
    int player1 ;
    int player2 ;
    boolean updated = false;
    int sendval ;
    int recval ;
    boolean rec = false;
    Boolean connected ;
    DataOutputStream outToServer ;
    BufferedReader infromserver ;
   // TextView textResponse;
    Client(String addr, int port,String textResponse) {
        dstAddress = addr;
        dstPort = port;
        response=textResponse;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

         socket = null;

        try {

            socket = new Socket("10.8.16.26", 6789);
            connected = true;
             outToServer = new DataOutputStream(socket.getOutputStream());
             infromserver = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            //sendToServer(2);
            //sendToServer(2);
       recval  = inputfromserver() ;
            player1 = recval ;
            player2 = (1+player1)%3==0?1:2;
            updated = true;

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
            connected = false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
            connected = false;
        }
        while(true){
            if(send){
                sendToServer(sendval) ;
                send = false;
            }
            if(rec){
                updated = false ;
                recval=inputfromserver() ;
                updated  =true;
                rec= false;
            }
        }

    }

    public  void sendToServer(int x){
        if(connected){
            String send = Integer.toString(x) ;
            send= send+"\n" ;
            try {
                outToServer.writeBytes(send);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
    public  int inputfromserver(){
        if(connected){
            try {
                String inp =infromserver.readLine() ;
                int ret = Integer.valueOf(inp) ;
                return ret ;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        else return -1 ;
    }

    @Override
    protected void onPostExecute(Void result) {
     //   textResponse.setText(response);
        super.onPostExecute(result);

    }


}
