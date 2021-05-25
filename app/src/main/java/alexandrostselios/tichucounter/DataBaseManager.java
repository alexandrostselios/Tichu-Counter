package alexandrostselios.tichucounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static alexandrostselios.tichucounter.GUI.TextScore1;
import static alexandrostselios.tichucounter.GUI.mydatabase;
import static android.os.Environment.DIRECTORY_DOCUMENTS;

public class DataBaseManager extends Activity {

    private Intent intent;
    private Context context;
    private File file;
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
        int i;
        String index;
        writeData = intent.getStringArrayExtra("score");
        Cursor resultSet = mydatabase.rawQuery("SELECT max(ID) FROM Teams;",null);
        resultSet.moveToFirst();
        index = resultSet.getString(0);
        for(i=0;i<writeData.length;i=i+2){
            if(writeData[i]!=null){
                mydatabase.execSQL("INSERT INTO ScoreHistory(TeamID,Score1,Score2) VALUES("+Integer.parseInt(index)+","+Integer.parseInt(writeData[i])+","+Integer.parseInt(writeData[i+1])+");");
            }else{
                break;
            }
        }
        mydatabase.execSQL("INSERT INTO FinalScore(TeamID,Score1,Score2) VALUES("+Integer.parseInt(index)+","+Integer.parseInt(writeData[i-2])+","+Integer.parseInt(writeData[i-1])+");");
    }

    public void readData() {
        String index;
        Cursor resultSet = mydatabase.rawQuery("SELECT count(*) FROM FinalScore ORDER BY ID DESC;",null);
        resultSet.moveToFirst();
        if(resultSet.getInt(0)>0){
            resultSet = mydatabase.rawQuery("SELECT * FROM FinalScore ORDER BY ID DESC;",null);
            resultSet.moveToFirst();
            index = resultSet.getString(2);
            GUI.TextScore1.setText(index);
            index = resultSet.getString(3);
            GUI.TextScore2.setText(index);
            Toast.makeText(this.context, "Game was Loaded succesfully!!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void revertScore(){
        String index;
        Cursor resultSet = mydatabase.rawQuery("SELECT count(*) FROM FinalScore ORDER BY ID DESC;",null);
        resultSet.moveToFirst();
        if(resultSet.getInt(0)>0){
            resultSet = mydatabase.rawQuery("SELECT * FROM FinalScore ORDER BY ID DESC;",null);
            resultSet.moveToFirst();
            index = resultSet.getString(2);
            GUI.TextScore1.setText(index);
            index = resultSet.getString(3);
            GUI.TextScore2.setText(index);
        }
    }
}