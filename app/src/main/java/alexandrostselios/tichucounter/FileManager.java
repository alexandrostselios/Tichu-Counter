package alexandrostselios.tichucounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.util.Log;
import android.view.View;
import android.view.Window;

import java.util.Date;
import java.util.Locale;

import java.text.SimpleDateFormat;

import android.app.ProgressDialog;
import android.widget.ProgressBar;

import static android.os.Environment.DIRECTORY_DOCUMENTS;

public class FileManager extends Activity {

    private Intent intent;
    private Context context;
    private File file;
    private FileInputStream fileInputtStream;
    private FileOutputStream fileOutputStream;
    private OutputStreamWriter outputStreamWriter;
    private String currentDate;
    private String[] writeData = new String[200];
    private boolean append;

    public FileManager(Intent intent, Save context) throws IOException {
        this.intent=intent;
        this.context=context;
    }

    public FileManager(Intent intent, Load context) throws IOException {
        this.intent=intent;
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveDataToFile() throws IOException {
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        writeData = intent.getStringArrayExtra("score");
        openFile();
        writeDataToFile();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void readDataFromFile() throws IOException {
       openFile();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openFile() throws IOException {
        //https://stackoverflow.com/questions/21216943/how-to-access-getfilesdir-as-an-environment-variable
        //https://gist.github.com/lopspower/76421751b21594c69eb2
        file = new File( String.valueOf(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS)),"/score.txt");
        if(file.exists()){
            append = true;
        }else{
            append = false;
            file.createNewFile();
        }
    }

    private void writeDataToFile() throws IOException {
        int line = 1;
        fileOutputStream = new FileOutputStream(file,append);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(currentDate+"\n");
        for(int i=0;i<writeData.length;i=i+2){
            if(writeData[i]!=null){
                outputStreamWriter.write(line +") "+writeData[i]+" "+writeData[i+1]+"\n");
                line++;
            }
        }
        outputStreamWriter.write(10);
        outputStreamWriter.close();
    }

    private void readDataToFile() throws IOException {
        fileInputtStream = new FileInputStream(file);
        fileInputtStream.read();
    }
}