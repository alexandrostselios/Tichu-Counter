package alexandrostselios.tichucounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

public class Save extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_menu_save);
        

        // Create a FileManager object to use files
        try {
            FileManager fileManager = new FileManager(getIntent(),this);
            fileManager.saveDataToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}