package alexandrostselios.tichucounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static alexandrostselios.tichucounter.GUI.mydatabase;
import static android.os.Environment.DIRECTORY_DOCUMENTS;

public class DataBaseManager extends Activity {

    private Intent intent;
    private Context context;
    private File file;
    private FileOutputStream fileOutputStream;
    private OutputStreamWriter outputStreamWriter;
    private String currentDate;
    private String[] writeData = new String[200];
    private boolean append;

    public DataBaseManager(Intent intent, Save context) throws IOException {
        this.intent=intent;
        this.context=context;
    }

    public DataBaseManager(Intent intent, Load context) throws IOException {
        this.intent=intent;
        this.context=context;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveData() throws IOException {
        openFile();
        saveDataToDataBase();
    }

    private void saveDataToDataBase() throws IOException {
        int line = 0;
        writeData = intent.getStringArrayExtra("score");
        //outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file,append));
        //outputStreamWriter.write(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date())+"\n");

        Cursor resultSet = mydatabase.rawQuery("SELECT max(ID) FROM Teams;",null);
        resultSet.moveToFirst();
        String index = resultSet.getString(0);
        int i;
        for(i=0;i<writeData.length;i=i+2){
            if(writeData[i]!=null){
                mydatabase.execSQL("INSERT INTO ScoreHistory VALUES("+Integer.parseInt(index)+","+Integer.parseInt(writeData[i])+","+Integer.parseInt(writeData[i+1])+");");
                //outputStreamWriter.write(line +":"+writeData[i]+":"+writeData[i+1]+"\n");
                line++;
            }else{
                i=300;
            }
        }
        //int temp = Integer.parseInt(index);
        mydatabase.execSQL("INSERT INTO FinalScore VALUES("+Integer.parseInt(index)+","+Integer.parseInt(writeData[line-1])+","+Integer.parseInt(writeData[line])+");");

        //outputStreamWriter.write(10);
        //outputStreamWriter.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void readData() throws IOException {
        openFile();
        readDataFromFile();
    }

    private void readDataFromFile() throws IOException {
        int i=1;
        String[] score;
        String line,team1 = null,team2=null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder out = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            if(line.startsWith(i+":")){
                score = line.split(":");
                team1 = score[1];
                team2 = score[2];
                i++;
            }
        }
        if(team1 != null && team2 != null){
            intent.putExtra("score1",team1);
            intent.putExtra("score2",team2);
        }
    }
}