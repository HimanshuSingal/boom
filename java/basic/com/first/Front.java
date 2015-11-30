package basic.com.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class Front extends AppCompatActivity {
    public void network_act(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("cols",6);
        intent.putExtra("rows",6);

        startActivity(intent);

    }

    public void single_act(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MainActivity_single.class);
        EditText cols = (EditText) findViewById(R.id.cols);
        EditText rows = (EditText) findViewById(R.id.rows);

        intent.putExtra("cols", cols.getText().toString());
        intent.putExtra("rows", rows.getText().toString());

        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);


    }

}
