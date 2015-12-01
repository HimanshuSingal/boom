/**
 * Created by $rohit on 11/16/2015.
 * This is MainActivity Class used to Display grid and manipulate 
 *   
 */


package basic.com.first;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import basic.com.*;

//  Class preclass is used to create thread 

class preclass extends Thread
{
    MainActivity th=new MainActivity();
    Client user;//=new Client("localhost", 45353, response);
    long TIMEMAX = 1000000000;
    public preclass(MainActivity th1)
    {
            th = th1 ;                             //  MainActivity Object 
            user = th.user ;
    }
    @Override                                       // thread will be created and it will in background 
    public void run()
    { 
         user.rec = true;                              //  user wants to rec new value from server  
        long starttime = System.nanoTime() ;           //  When Started To Wait for input  
        starttime /= TIMEMAX ;
        while(!user.updated){                           //    check if val is updated in server 
                long   currenttime =  System.nanoTime()  ;    
                   currenttime /= TIMEMAX;
            if(currenttime - starttime > 20 )break ;         // for updation till 20 sec after that it will stop waiting 
        }
        if(user.updated) {                               // If user val is updated 
            user.updated = false;
            final int rr;
            if(user.recval != -2)                        // user.recval == -2 means other hasnot clicked till limit
                     rr = Parameters.clicked(user.recval, th.tu2); 
            else rr = -2 ;
            th.gridview.post(new Runnable() {
                public void run() {                 //     thread that will make changes in grid 
                    th.current = th.tu2 ;           
                    th.makechanges(rr);
                    if (rr == -2) {                  // Declare winner 
                        if (th.tu1 == 1) {
                            Toast.makeText(th, "Player 2 win (LATE Player 1 )!",
                                    Toast.LENGTH_SHORT).show();
                            th.score = 0;
                        } else {
                            Toast.makeText(th, "Player 1 win (LATE Player 2 )!",
                                    Toast.LENGTH_SHORT).show();
                            th.score = 0;
                        }
                        // th.gridview.setAdapter(new ImageAdapter(th.a));
                    }
                }
            });
        }
        else {
            user.sendval = -2 ;          // if no val rec from server in 20 sec  .
            user.send= true;             // send -2 to server means player other player value wasnot rec in time . 
            th.gridview.post(new Runnable() {
                public void run() {             // run thread to access grid 
                    th.current = th.tu1;
                    th.makechanges(-2);           // reset whole parameter matrix 
                    if(th.tu1 == 1) {              // declare winner 
                        Toast.makeText(th, "Player 1 win (LATE Player 2 )!",
                                Toast.LENGTH_SHORT).show();
                        th.score = 0;

                    }
                    else {
                        Toast.makeText(th, "Player 2 win (LATE Player 1 )!",
                                Toast.LENGTH_SHORT).show();
                        th.score = 0;
                    }
                    // th.gridview.setAdapter(new ImageAdapter(th.a));
                }
            });
        }

//

    }
}

public class MainActivity extends AppCompatActivity {
    public boolean check_player2=false;
    public  int tu1 = 1;                         //  this player id 
        public  int tu2;                         // other player id 
        Client user;                               // object for client 
    int current = 0;
    boolean entered = false;
        public  int score = 0 ;
        String response;
        public GridView gridview;                   // grid to display matrix
        public Context a;
        int rr;
        MainActivity th = this ;
        // function will make changes in grid via  looking at parameters matrix 
        // it will check END cases and declare result
        public void makechanges(int rr) {          
            if (rr != -1 && rr != -2) {
                score++;
                gridview.setAdapter(new ImageAdapter(a));
            } else if (rr == 0) {
                if(current == 1) {                 // declare winner 
                    Toast.makeText(MainActivity.this, "Player 1 wins !(Cleared Player 2)",
                            Toast.LENGTH_SHORT).show();
                    score = 0;
                }
                else {
                    Toast.makeText(MainActivity.this, "Player 2 wins !(Cleared Player 1)",
                            Toast.LENGTH_SHORT).show();
                    score = 0;
                }
                }
            else if(rr == -2){             // if no input from player in time then reset the matrix 
                Parameters.reset();
                gridview.setAdapter(new ImageAdapter(a));       // Change grid 
            }
            if (score > 2) {
                boolean test = Parameters.checkEnd();
                if (test) {
                    if(current == 1) {            // Declare Result 
                        Toast.makeText(MainActivity.this, "Player 1 wins !(Cleared Player 2)",
                                Toast.LENGTH_SHORT).show();
                        score = 0;
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Player 2 wins !(Cleared Player 1)",
                                Toast.LENGTH_SHORT).show();
                        score = 0;
                    }

                }
            }
        }


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            user = new Client("localhost", 45353, response);   // Connect to server and create and object of client 
            user.execute();                                    // will run client in background 
            while (!user.updated) {                            //  waiting for server to update value on server

            }
            tu1 = user.recval;
            user.updated = false;
            gridview = (GridView) findViewById(R.id.gridview);      
            gridview.setNumColumns(Parameters.col_size);
            new Parameters();
            gridview.setAdapter(new ImageAdapter(this));    // setting grid 
            a = this;
            tu2 = (tu1 + 1) % 3 == 0 ? 1 : 2;
            score = 0;
            if(tu1 == 2){
                user.rec  = true ;
                while(!user.updated){                  // for the case when this user is player two then leting other player click first

                }
                user.updated = false;
                rr = Parameters.clicked(user.recval, tu2);
                current = tu2 ;
                makechanges(rr);
            }
            // whenever user clicks on grid this function will be excuted 
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    if (!user.rec) {                   // if user is not waiting for server output
                        rr = Parameters.clicked(position, tu1);
                        if(rr !=-1) {
                            entered  = true ;
                            current = tu1 ;
                            makechanges(rr);
                            user.sendval = position;
                            user.send = true;
                            preclass play2 = new preclass(th);
                            //  play2.preclass1(th) ;
                            play2.start();
                        }
                    }
                }
            });
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_re) {
                Parameters.reset();
                gridview.setAdapter(new ImageAdapter(a));

                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }
