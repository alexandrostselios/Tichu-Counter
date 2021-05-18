package alexandrostselios.tichucounter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class Load extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(null,"LOAD");
        //setContentView(R.layout.activity_menu_load);

        // Create a FileManager object to use files
        try {
            FileManager fileManager = new FileManager(getIntent(),this);
            fileManager.readDataFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }
}