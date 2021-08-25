package alexandrostselios.tichucounter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.sql.Types.NULL;

public class DataBaseManager extends Activity {

    private static Context context;
    private String[] writeData = new String[200];
    private static SQLiteDatabase mydatabase;
    private final String server_url = "http://alefhome.ddns.net:2374/tichucounter/insert.php";
    private RequestQueue queue = null;
    public static int start = 0;
    int MAX_SERIAL_THREAD_POOL_SIZE = 1;

    public DataBaseManager (SQLiteDatabase mydatabase,Context context){
        this.mydatabase=mydatabase;
        this.context = context;
    }

    public DataBaseManager() {

    }

    private void writeToOnlineDatabase(int flag, String TeamID, int score1, int score2){
        queue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                synchronized (new Object()){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
/*                        String message = jsonObject.getString("message");
                        String teamID = jsonObject.getString("TeamID");
                        String NameTeam1 = jsonObject.getString("NameTeam1");
                        String NameTeam2 = jsonObject.getString("NameTeam2");*/
                        if(success.equals("1") && flag == 0){
                            Log.d(null,"++++++++++++++++++++++++++++++");
                            Log.d(null,jsonObject.toString());
/*                            Log.d(null,"Response: : " + success + "\n");
                            Log.d(null,"Message: " + message + "\n");
                            Log.d(null, "TeamID: " + teamID + "\n");
                            Log.d(null,"NameTeam1: " + NameTeam1 + "\n");
                            Log.d(null,"NameTeam2: " + NameTeam2 + "\n");*/
                            Log.d(null,"++++++++++++++++++++++++++++++");
                        }else if(success.equals("1") && flag == 1){
                            Log.d(null,"++++++++++++++++++++++++++++++");
                            Log.d(null,jsonObject.toString());
/*                            Log.d(null,"Response: : " + success + "\n");
                            Log.d(null,"Message: " + message + "\n");
                            Log.d(null, "TeamID: " + teamID + "\n");
                            Log.d(null,"NameTeam1: " + NameTeam1 + "\n");
                            Log.d(null,"NameTeam2: " + NameTeam2 + "\n");
                            Log.d(null,"ScoreTeam1: " + jsonObject.getString("ScoreTeam1") + "\n");
                            Log.d(null,"ScoreTeam2: " + jsonObject.getString("ScoreTeam2") + "\n");*/
                            Log.d(null,"++++++++++++++++++++++++++++++");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(null,"=======++++++++=========----------/  "+error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                if(flag == 0){
                    params.put("Table","Teams");
                    params.put("NameTeam1","Alex");
                    params.put("NameTeam2","Tselios");
                }else if(flag == 1){
                    params.put("Table","ScoreHistory");
                    params.put("ScoreTeam1",String.valueOf(score1));
                    params.put("ScoreTeam2",String.valueOf(score2));
                }else{
                    params.put("Table","FinalScore");
                    params.put("ScoreTeam1",String.valueOf(score1));
                    params.put("ScoreTeam2",String.valueOf(score2));
                }
                params.put("TeamID", TeamID);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void createDatabase() {
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Teams(ID INTEGER PRIMARY KEY AUTOINCREMENT,Team1 VARCHAR,Team2 VARCHAR);");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS FinalScore(ID INTEGER PRIMARY KEY AUTOINCREMENT, TeamID INTEGER NOT NULL,Score1 INTEGER, Score2 INTEGER, FOREIGN KEY (ID) REFERENCES Teams (ID));");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS ScoreHistory(ID INTEGER PRIMARY KEY AUTOINCREMENT,TeamID INTEGER NOT NULL,Score1 INTEGER, Score2 INTEGER, FOREIGN KEY (ID) REFERENCES Teams (ID));");
        mydatabase.execSQL("INSERT INTO Teams(Team1,Team2) VALUES('Alexandros','Tselios');");
    }

    public void  saveRoundScore(int score1,int score2){
        String index;
        Cursor resultSet = mydatabase.rawQuery("SELECT max(ID) FROM Teams;",null);
        resultSet.moveToFirst();
        index = resultSet.getString(0);
       // mydatabase.execSQL("INSERT INTO ScoreHistory(TeamID,Score1,Score2) VALUES("+Integer.parseInt(index)+","+score1+","+score2+");");
        if(start == 0){
            writeToOnlineDatabase(0,index,0,0);
            start=1;
        }
        writeToOnlineDatabase(1,index,score1,score2);
    }

    public static void revertScore(){
        String score;
        Cursor resultSet1 = mydatabase.rawQuery("SELECT * FROM ScoreHistory ORDER BY ID DESC;",null);
        //resultSet.moveToFirst();
       // Cursor resultSet1 = mydatabase.rawQuery("SELECT * FROM ScoreHistory WHERE ID = (SELECT MAX(ID) FROM ScoreHistory) - 1;",null);
        Log.d(null,resultSet1.getColumnName(0)+" "+resultSet1.getColumnName(1)
        +" "+resultSet1.getColumnName(2)+" "+resultSet1.getColumnName(3));
        resultSet1.moveToFirst();
        Log.d(null, String.valueOf(resultSet1.getString(2)));

        if(resultSet1.getCount()>0){
            //resultSet.close();
            //Cursor resultSet1 = mydatabase.rawQuery("SELECT * FROM ScoreHistory WHERE ID = (SELECT MAX(ID) FROM ScoreHistory) - 1;",null);
            //resultSet1.moveToFirst();
            //score = resultSet1.getString(2);
            Log.d(null,"---------============== "+resultSet1.getString(2));
           GUI.TextScore1.setText("66");
//            score = resultSet1.getString(3);
//            GUI.TextScore2.setText(score);
        }
    }

    public static void saveFinalScore(int score1,int score2) {
        String index;
        Cursor resultSet = mydatabase.rawQuery("SELECT max(ID) FROM Teams;",null);
        resultSet.moveToFirst();
        index = resultSet.getString(0);
        mydatabase.execSQL("INSERT INTO FinalScore(TeamID,Score1,Score2) VALUES("+Integer.parseInt(index)+","+score1+","+score2+");");
        DataBaseManager db = new DataBaseManager();
        db.writeToOnlineDatabase(2,index,score1,score2);
    }

    public void loadScore() {
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