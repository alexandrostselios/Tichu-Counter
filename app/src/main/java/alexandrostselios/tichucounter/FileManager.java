package alexandrostselios.tichucounter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

public class FileManager {

    private final Intent intent;
    private final Context context;
    private File file;
    private OutputStreamWriter outputStreamWriter;
    private Date currentDate;
    private String writeData;

    public FileManager(Intent intent,Context context) throws IOException {
        this.intent=intent;
        this.context=context;
    }

    public void saveDataToFile() throws IOException {
        openFile();
        currentDate = Calendar.getInstance().getTime();
        writeData = intent.getStringExtra("score");
        Log.d(null, writeData);
        writeDataToFile();
    }

    public void readDataFromFile(){

    }

    private void openFile() throws FileNotFoundException {
        file = new File("/data/data/alexandrostselios.tichucounter/files/test.txt");
        outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file.getName().toString(), Context.MODE_APPEND));
    }

    private void writeDataToFile() throws IOException {
        outputStreamWriter.write(currentDate +" "+writeData+"\n");
        outputStreamWriter.write(10);
        outputStreamWriter.close();
    }
}