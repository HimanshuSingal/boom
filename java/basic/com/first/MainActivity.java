package basic.com.first;
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

class preclass extends Thread
{
    MainActivity th ;
    Client user;
    public void preclass1(MainActivity th1, Client user1)
    {
            th = th1 ;
            user = user1 ;
    }
    @Override
    public void run()
    {
         user.rec = true;
            while (!user.updated) {

            }
            int rr ;
            rr = Parameters.clicked(user.recval,th.tu2);
            th.makechanges(rr);               
    } 
}

public class MainActivity extends AppCompatActivity {

        public  int tu1 = 1;
        public  int tu2;
        Client user;
        public  int score;
        String response;
        public GridView gridview;
        public Context a;
        int rr;
        MainActivity th = this ;
        public void makechanges(int rr) {
            if (rr != -1) {
                score++;
                gridview.setAdapter(new ImageAdapter(a));
            } else if (rr == 0) {
                //Toast.makeText(MainActivity.this, "Game Over!"+score  ,
                //      Toast.LENGTH_SHORT).show();score=0;
            }
            if (score >= 2) {
                boolean test = Parameters.checkEnd();
                if (test) {
                    //Toast.makeText(MainActivity.this, "Game Over!" + score,
                    //      Toast.LENGTH_SHORT).show();
                    score = 0;
                }
            }
        }

        public void startgame() {
            user.rec = true;
            while (!user.updated) {

            }
            rr = Parameters.clicked(user.recval, tu2);
            makechanges(rr);
        }
    }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            user = new Client("localhost", 45353, response);
            user.execute();
            while (!user.updated) {

            }
            tu1 = user.recval;
            user.updated = false;
            gridview = (GridView) findViewById(R.id.gridview);
            gridview.setNumColumns(Parameters.col_size);
            new Parameters();
            gridview.setAdapter(new ImageAdapter(this));
            a = this;
            tu2 = (tu1 + 1) % 3 == 0 ? 1 : 2;
            score = 0;
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    while (user.rec) {

                    }
                    rr = Parameters.clicked(position, tu1);
                    makechanges(rr);
                    user.sendval = position;
                    user.send = true;

                    preclass play2 = null;
                    play2.preclass1(th , user) ;
                    play2.start(); 
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
