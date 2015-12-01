package basic.com.first;

/**
 * Created by $rohit on 11/16/2015.
 * This is CLient Class  used for Connecting player to server and exchanging information with server 
 *   
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

    String dstAddress;              //    Proxy address
    int dstPort;                     //  port address
    public Socket socket ;          //   Socket Variable 
    String response = "";   
    boolean send = false ;          //  Boolean variable which checks whether user wants to send something to server or not                                                         
    int player1 ;                               
    int player2 ;
    boolean updated = false;        //   Boolean variable which checks whether the information on server is updated or not   
    int sendval ;                    //   The value which user wants to send to server         
    int recval ;                     // The value which user will receieve from server
    boolean rec = false;             // Boolean variable which checks whether to receieve value from  server or not 
    Boolean connected ;
    DataOutputStream outToServer ;   // Variable used to send message through socket 
    BufferedReader infromserver ;    // Variable used to  receieve message from socket 
   // TextView textResponse;

    // Constructor takes IP and Port as argument 
    Client(String addr, int port,String textResponse) {
        dstAddress = addr;
        dstPort = port;
        response=textResponse;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

         socket = null;                   // initializing the socket 

        try {

            socket = new Socket("10.8.16.26", 6789);               // connecting to server
            connected = true;                                      // connected
             outToServer = new DataOutputStream(socket.getOutputStream());         
             infromserver = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            //sendToServer(2);
            //sendToServer(2);
       recval  = inputfromserver() ;                      // receieving player id from 
            player1 = recval ;                                     
            player2 = (1+player1)%3==0?1:2;
            updated = true;                                // value at server has been updated

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
        while(true){                                              // This loop will run in background infinite time 
            if(send){                  // whenever send is true means user wants to send something than 
                sendToServer(sendval) ;             //  socket will send sendval to server
                send = false;                   
            }
            if(rec){                        // whenever rec is true means user wants to recieve something 
                updated = false ;
                recval=inputfromserver() ;    // socket will recieve val  from server and store it in recval
                updated  =true;
                rec= false;
            }
        }

    }
  

  //   Function used to send value to server 

    public  void sendToServer(int x){
        if(connected){                             //        if socket is connected to server 
            String send = Integer.toString(x) ;    //         convert the argument into string 
            send= send+"\n" ;
            try {
                outToServer.writeBytes(send);        // send it to server
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }


    // Function used to recieve value from server 
    public  int inputfromserver(){
        if(connected){                           //   if connected to server 
            try {
                String inp =infromserver.readLine() ;      // Read a string from server 
                int ret = Integer.valueOf(inp) ;           // convert it to int 
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
