package basic.com.first;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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


public class MainActivity_single extends AppCompatActivity {

    public static TextView msg;
    public static int tu ;
    public int score;
    public GridView gridview;
    public Context a;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        int tmp1=Integer.parseInt(intent.getStringExtra("cols"));
        int tmp2=Integer.parseInt(intent.getStringExtra("rows"));

        Parameters_s.set_size(tmp1,tmp2);

        tu = 1 ;
        score = 0 ;
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(Parameters_s.col_size);
        new Parameters_s();
        gridview.setAdapter(new ImageAdapter_s(this));
        a=this;
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                int  rr =  Parameters_s.clicked(position, tu);
                if(rr!=-1) {
                    score++;

                    gridview.setAdapter(new ImageAdapter_s(a));
                    changetu();
                }
                //when same states appear again and again
                if(rr==0){
                    Toast.makeText(MainActivity_single.this, "Game Over!"+score  ,
                            Toast.LENGTH_SHORT).show();score=0;}
                if(score>=2)
                {
                    boolean test=Parameters_s.checkEnd();
                    if(test) {
                        Toast.makeText(MainActivity_single.this, "Game Over!" + score,
                                Toast.LENGTH_SHORT).show();
                        score = 0;
                    }
                }
            }
            public void changetu(){
                if(tu==1)tu=2;
                else tu=1;
            }
        });


/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "lets see", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
            Parameters_s.reset();
            gridview.setAdapter(new ImageAdapter_s(a));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
