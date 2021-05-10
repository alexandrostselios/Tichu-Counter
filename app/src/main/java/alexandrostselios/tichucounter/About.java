package alexandrostselios.tichucounter;

import android.content.Context;
import android.content.Intent;
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

    public void writeToFile(String data, Context context) {
        try {
            Log.d(null, "inside try");
            File file = new File("/data/data/alexandrostselios.tichucounter/files/test.txt");
            //Log.d(null,String.valueOf(file.getName()) + " ++ "+ file.getPath());

            if (file.exists()) {
                Log.d(null, "exist");
            }else{
                Intent intent = getIntent();
                String str = intent.getStringExtra("key");
                Log.d(null, str);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file.getName().toString(), Context.MODE_PRIVATE));
                outputStreamWriter.write(str+" alexandros "+" ANDROID");
                outputStreamWriter.close();
            }
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}