package alexandrostselios.tichucounter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.Calendar;
import java.util.Date;

import static android.os.Environment.DIRECTORY_DOCUMENTS;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

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
        writeDataToFile();
    }

    public void readDataFromFile(){

    }

    private void openFile() throws IOException {
        //https://stackoverflow.com/questions/21216943/how-to-access-getfilesdir-as-an-environment-variable
        //https://gist.github.com/lopspower/76421751b21594c69eb2
        file = new File( String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)),"/tichu.txt");
        Log.d(null,file.getAbsolutePath());
        outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file.getName().toString(), Context.MODE_APPEND));
    }

    private void writeDataToFile() throws IOException {
        for(int i=0;i<writeData.length;i=i+2){
            if(writeData[i]!=null){
                outputStreamWriter.write(currentDate +" "+writeData[i]+" "+writeData[i+1]+"\n");
            }
        }
        outputStreamWriter.write(10);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}