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

            socket = new Socket("10.8.19.218", 6789);
            connected = true;
             outToServer = new DataOutputStream(socket.getOutputStream());
             infromserver = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            //sendToServer(2);
            //sendToServer(2);
       recval  = inputfromserver() ;
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
                sendToServer(sendval);
                send = false;
            }
            if(rec){
                updated = false;
                recval = inputfromserver() ;
                updated = true;
                rec= false;
            }
        }
    }

    public  void sendToServer(int x){
     /*   connected = true;
        DataOutputStream outToServer = null;
        BufferedReader infromserver = null ;
        try {
            socket = new Socket("10.8.19.218", 6789);
            outToServer = new DataOutputStream(socket.getOutputStream());
            infromserver = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
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
      /*  DataOutputStream outToServer = null;
        BufferedReader infromserver = null ;
        try {
            outToServer = new DataOutputStream(socket.getOutputStream());
            infromserver = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        */if(connected){
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