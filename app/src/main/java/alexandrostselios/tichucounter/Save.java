package alexandrostselios.tichucounter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;

public class Save extends AppCompatActivity {

    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_save);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        // Create a FileManager object to use files
        try {
            FileManager fileManager = new FileManager(getIntent(),this);
            fileManager.saveDataToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        progressBar.setVisibility(View.INVISIBLE);
    }
}