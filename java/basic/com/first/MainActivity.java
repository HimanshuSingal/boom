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


public class MainActivity extends AppCompatActivity {

    public static TextView msg;
    public static int tu1= 1 ;
    public static int tu2 ;
    Client user ;
    public int score;
    String response ;
    public GridView gridview;
    public Context a;
    boolean waiting = false;
    Button sd ;
    Button md ;
    int rr ;

    void makechanges(int rr){
        if(rr!=-1) {
            score++;
            gridview.setAdapter(new ImageAdapter(a));
        }
        else if(rr==0){
            Toast.makeText(MainActivity.this, "Game Over!"+score  ,
                    Toast.LENGTH_SHORT).show();score=0;}
        if(score>=2)
        {
            boolean test=Parameters.checkEnd();
            if(test) {
                Toast.makeText(MainActivity.this, "Game Over!" + score,
                        Toast.LENGTH_SHORT).show();
                score = 0;
            }
        }
    }
    void initial(){
        tu2 = (tu1+1)%3 == 0? 1 : 2 ;
        score = 0 ;
        if(tu1 == 2 ) {
            int p =1 ;
            waiting = true;
            user.rec= true;
            while(!user.updated){
            }
            p = user.recval  ;
            waiting = false;
            rr = Parameters.clicked(p, 1);
            makechanges(rr);
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = new Client("10.8.16.26",6789,response);
       user.execute();
        while(!user.updated){

        }
        tu1 = user.recval ;
        user.updated = false;
      //  user.sendval = 3 ;
        //user.send = true ;
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(Parameters.col_size);
        new Parameters();
        gridview.setAdapter(new ImageAdapter(this));
        a=this;
        initial();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    rr = Parameters.clicked(position, tu1);
                    makechanges(rr);
                    user.sendval = position ;
                    user.send = true ;
                    int position2 = 1;
                    user.rec = true ;
                    while(!user.updated) {
                    }
                   position2 =  user.recval ;
                    rr = Parameters.clicked(position2, tu2);
                    makechanges(rr);
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
