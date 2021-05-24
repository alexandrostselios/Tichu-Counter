package alexandrostselios.tichucounter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;

import java.io.IOException;

public class Load extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Create a DataBaseManager object to use files
        try {
            DataBaseManager dataBaseManager = new DataBaseManager(getIntent(),this);
            dataBaseManager.readData();
            GUI.TextScore1.setText(getIntent().getStringExtra("score1"));
            GUI.TextScore2.setText(getIntent().getStringExtra("score2"));
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }
}