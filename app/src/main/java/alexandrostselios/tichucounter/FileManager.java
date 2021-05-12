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

    private Intent intent;
    private Context context;
    private File file;
    private OutputStreamWriter outputStreamWriter;
    private Date currentDate;
    private String[] writeData = new String[200];

    public FileManager(Intent intent, Save context) throws IOException {
        this.intent=intent;
        this.context=context;
    }

    public void saveDataToFile() throws IOException {
        openFile();
        currentDate = Calendar.getInstance().getTime();
        writeData = intent.getStringArrayExtra("score");
//        Log.d(null, String.valueOf(writeData.length));
//        for(int i=0;i<writeData.length;i++){
//            if(writeData[i]!=null){
//                Log.d(null, writeData[i]);
//            }
//        }
        writeDataToFile();
    }

    public void readDataFromFile(){

    }

    private void openFile() throws FileNotFoundException {
        file = new File("/data/data/alexandrostselios.tichucounter/files/test.txt");
        outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file.getName().toString(), Context.MODE_APPEND));
    }

    private void writeDataToFile() throws IOException {
        for(int i=0;i<writeData.length;i=i+2){
            if(writeData[i]!=null){
                outputStreamWriter.write(currentDate +" "+writeData[i]+" "+writeData[i+1]+"\n");
            }
        }
        outputStreamWriter.write(10);
        outputStreamWriter.close();
    }
}