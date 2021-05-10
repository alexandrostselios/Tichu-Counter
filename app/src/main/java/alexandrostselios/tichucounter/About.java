package alexandrostselios.tichucounter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        writeToFile("test",this);
        setContentView(R.layout.activity_menu_about);

    }

    private void writeToFile(String data, Context context) {
        try {
            Log.d(null, "inside try");
            File file = new File("/data/data/alexandrostselios.tichucounter/files/test.txt");
            if (file.exists()) {
                Log.d(null, "EXIST");
            }else{
                Log.d(null, "does not EXIST");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("test.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(data);
                outputStreamWriter.close();
            }
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}