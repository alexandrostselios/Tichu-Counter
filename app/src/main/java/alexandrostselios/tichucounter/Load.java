package alexandrostselios.tichucounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;

public class Load extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Create a FileManager object to use files
        try {
            FileManager fileManager = new FileManager(getIntent(),this);
            fileManager.readData();
            GUI.TextScore1.setText(getIntent().getStringExtra("score1"));
            GUI.TextScore2.setText(getIntent().getStringExtra("score2"));
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }
}