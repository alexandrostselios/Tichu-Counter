package alexandrostselios.tichucounter;

// token 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

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Types.NULL;

public class DataBaseManager extends Activity {

    private static Context context;
    private String[] writeData = new String[200];
    private String[] teamsScore = new String[4];
    private static SQLiteDatabase mydatabase;
    private final String Write_Server_URL = "http://alefhome.ddns.net:2374/tichucounter/insert.php";
    private static String Read_Server_URL = "http://alefhome.ddns.net:2374/tichucounter/retrieve.php";
    private RequestQueue WriteQueue = null;
    private RequestQueue readQueue = null;
    public static int start = 0;
    int MAX_SERIAL_THREAD_POOL_SIZE = 1;

    public DataBaseManager (SQLiteDatabase mydatabase,Context context){
        this.mydatabase=mydatabase;
        this.context = context;
    }

    public DataBaseManager() {

    }

//    private void writeToOnlineDatabase(int flag, int TeamID, int score1, int score2){
//        WriteQueue = Volley.newRequestQueue(this.context);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Write_Server_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                synchronized (new Object()){
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String success = jsonObject.getString("success");
//                        String message = jsonObject.getString("message");
//                        String teamID = jsonObject.getString("TeamID");
//                        String NameTeam1 = jsonObject.getString("NameTeam1");
//                        String NameTeam2 = jsonObject.getString("NameTeam2");
//                        if(success.equals("1") && flag == 0){
//                            Log.d(null,"++++++++++++++++++++++++++++++");
//                            Log.d(null,jsonObject.toString());
//                            Log.d(null,"Response: : " + success + "\n");
//                            Log.d(null,"Message: " + message + "\n");
//                            Log.d(null, "TeamID: " + teamID + "\n");
//                            Log.d(null,"NameTeam1: " + NameTeam1 + "\n");
//                            Log.d(null,"NameTeam2: " + NameTeam2 + "\n");
//                            Log.d(null,"++++++++++++++++++++++++++++++");
//                        }else if(success.equals("1") && flag == 1){
//                            Log.d(null,"++++++++++++++++++++++++++++++");
//                            Log.d(null,jsonObject.toString());
//                            Log.d(null,"Response: : " + success + "\n");
//                            Log.d(null,"Message: " + message + "\n");
//                            Log.d(null, "TeamID: " + teamID + "\n");
//                            Log.d(null,"NameTeam1: " + NameTeam1 + "\n");
//                            Log.d(null,"NameTeam2: " + NameTeam2 + "\n");
//                            Log.d(null,"ScoreTeam1: " + jsonObject.getString("ScoreTeam1") + "\n");
//                            Log.d(null,"ScoreTeam2: " + jsonObject.getString("ScoreTeam2") + "\n");
//                            Log.d(null,"++++++++++++++++++++++++++++++");
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(null,"=======++++++++=========----------/  "+error.getMessage());
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                if(flag == 0){
//                    params.put("Table","Teams");
//                    params.put("NameTeam1","Alex");
//                    params.put("NameTeam2","Tselios");
//                }else if(flag == 1){
//                    params.put("Table","ScoreHistory");
//                    params.put("ScoreTeam1",String.valueOf(score1));
//                    params.put("ScoreTeam2",String.valueOf(score2));
//                }else{
//                    params.put("Table","FinalScore");
//                    params.put("ScoreTeam1",String.valueOf(score1));
//                    params.put("ScoreTeam2",String.valueOf(score2));
//                }
//                params.put("TeamID", String.valueOf(TeamID));
//                return params;
//            }
//        };
//        WriteQueue.add(stringRequest);
//    }

    private void read(int flag, String TeamID, int score1, int score2){
        WriteQueue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Read_Server_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                synchronized (new Object()){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //String success = jsonObject.getString("success");
                        String message = jsonObject.getString("TeamID");
                       /* String teamID = jsonObject.getString("TeamID");
                        String NameTeam1 = jsonObject.getString("NameTeam1");
                        String NameTeam2 = jsonObject.getString("NameTeam2");*/
                        if(flag == 0){
                            Log.d(null,"-------------------------------");
                            Log.d(null,jsonObject.toString());
                            Log.d(null,"Response: : " + message + "\n");
                            /*  Log.d(null,"Message: " + message + "\n");
                            Log.d(null, "TeamID: " + teamID + "\n");
                            Log.d(null,"NameTeam1: " + NameTeam1 + "\n");
                            Log.d(null,"NameTeam2: " + NameTeam2 + "\n");*/
                            Log.d(null,"-------------------------------");
                        }else if(flag == 1){
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
                       //Log.d(null,"lathos \n");
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
                /* if(flag == 0){
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
                params.put("TeamID", TeamID);*/
                return params;
            }
        };
        WriteQueue.add(stringRequest);
    }


   /* private void readFromOnlineDatabase(){
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(Read_Server_URL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "----=====\n");
                    }
                    //Log.d(null,"====++++++///// "+ sb.toString().trim());
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }*/

    /*private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject obj = jsonArray.getJSONObject(0);
        Log.d(null,obj.toString());
        teamsScore[0] = obj.getString("ID");
        teamsScore[1] = obj.getString("TeamID");
        teamsScore[2] = obj.getString("Score1");
        teamsScore[3] = obj.getString("Score2");
    }*/

    public void createDatabase() {
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Teams(ID INTEGER PRIMARY KEY AUTOINCREMENT,Team1 VARCHAR,Team2 VARCHAR);");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS FinalScore(ID INTEGER PRIMARY KEY AUTOINCREMENT, TeamID INTEGER NOT NULL,Score1 INTEGER, Score2 INTEGER, FOREIGN KEY (ID) REFERENCES Teams (ID));");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS ScoreHistory(ID INTEGER PRIMARY KEY AUTOINCREMENT,TeamID INTEGER NOT NULL,Score1 INTEGER, Score2 INTEGER, FOREIGN KEY (ID) REFERENCES Teams (ID));");
        mydatabase.execSQL("INSERT INTO Teams(Team1,Team2) VALUES('Alexandros','Tselios');");
        //writeToOnlineDatabase(0,163,0,0);
    }

    public void  saveRoundScore(int score1,int score2){
        String index;
        Cursor resultSet = mydatabase.rawQuery("SELECT max(ID) FROM Teams;",null);
        resultSet.moveToFirst();
        index = resultSet.getString(0);
        mydatabase.execSQL("INSERT INTO ScoreHistory(TeamID,Score1,Score2) VALUES("+Integer.parseInt(index)+","+score1+","+score2+");");
        /*if(start == 0){
           writeToOnlineDatabase(0,Integer.parseInt(index),score1,score2);
            start=1;
        }
        writeToOnlineDatabase(1,Integer.parseInt(index),score1,score2);*/
        resultSet.close();
    }

    public void revertScore(){
        String index =null;
        int score1 = 0;
        int score2 = 0;
        Cursor resultSet = mydatabase.rawQuery("SELECT * FROM ScoreHistory WHERE TEAMID =(SELECT max(ID) FROM Teams) ORDER BY ID DESC;",null);
        if(resultSet.getCount() == 0 || resultSet == null){
            score1=0;
            score2=0;
        }else{
            resultSet.moveToFirst();
            if (resultSet.getCount() > 0) {
                resultSet.moveToNext();
                score1 = resultSet.getInt(2);
                score2 = resultSet.getInt(3);
                //saveRoundScore(score1,score2);
                //writeToOnlineDatabase(1,Integer.parseInt(index),250,600);
            }
        }
        saveRoundScore(score1, score2);
        GUI.TextScore1.setText(String.valueOf(score1));
        GUI.TextScore2.setText(String.valueOf(score2));
        resultSet.close();
    }

    public static void saveFinalScore(int score1,int score2) {
        String index;
        Cursor resultSet = mydatabase.rawQuery("SELECT max(ID) FROM Teams;",null);
        resultSet.moveToFirst();
        index = resultSet.getString(0);
        mydatabase.execSQL("INSERT INTO FinalScore(TeamID,Score1,Score2) VALUES("+Integer.parseInt(index)+","+score1+","+score2+");");
        //DataBaseManager db = new DataBaseManager();
        //db.writeToOnlineDatabase(2,index,score1,score2);
        resultSet.close();
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
        resultSet.close();
    }
}