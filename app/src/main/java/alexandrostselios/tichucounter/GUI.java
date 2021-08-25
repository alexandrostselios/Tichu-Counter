package alexandrostselios.tichucounter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import org.w3c.dom.Text;

import static android.os.Build.ID;

public class GUI extends AppCompatActivity {

    private DataBaseManager dataBaseManager = null;

    private TextView roundScore = null;

    private Button tichu1 = null;
    private Button grandTichu1 = null;
    private Button tichu2 = null;
    private Button grandTichu2 = null;

    public static EditText TextScore1 = null;
    private EditText currentScore1 = null;
    public static EditText TextScore2 = null;
    private EditText currentScore2 = null;

    private CheckBox tichuCheck1 = null;
    private CheckBox grandTichuCheck1 = null;
    private CheckBox tichuCheck2 = null;
    private CheckBox grandTichuCheck2 = null;

    private int teamTichu1 = 0;
    private int teamGrandTichu1 = 0;
    private int score1 = 0;
    private int teamTichu2 = 0;
    private int teamGrandTichu2 = 0;
    private int score2 = 0;

    private TichuCounter tichuCounter=null;
    private boolean win = false;
    private int error=0;
    private Intent saveIntent;
    private int i,j;
    private String[] scoreArray = new String[200];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i=0;
        j=0;
        createButtons();
        createEditText();
        createCheckBox();
        createDatabase();
        playGame();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_game:
                clearScore();
                return true;
            case R.id.menu_save_game:
                DataBaseManager.saveFinalScore(tichuCounter.getScoreTeam1(),tichuCounter.getScoreTeam2());
                Toast.makeText(GUI.this, "Game was Saved succesfully!!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_load_game:
                dataBaseManager.loadScore();
                tichuCounter.setScoreTeam1(Integer.parseInt(String.valueOf(TextScore1.getText())));
                tichuCounter.setScoreTeam2(Integer.parseInt(String.valueOf(TextScore2.getText())));
                dataBaseManager.saveRoundScore(tichuCounter.getScoreTeam1(),tichuCounter.getScoreTeam2());
                Toast.makeText(GUI.this, "Game was Loaded succesfully!!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_revert:
                Toast.makeText(GUI.this, "Revert Score!!", Toast.LENGTH_SHORT).show();
                dataBaseManager.revertScore();
                tichuCounter.setScoreTeam1(Integer.parseInt(String.valueOf(TextScore1.getText())));
                tichuCounter.setScoreTeam2(Integer.parseInt(String.valueOf(TextScore2.getText())));
                //dataBaseManager.saveRoundScore(tichuCounter.getScoreTeam1(),tichuCounter.getScoreTeam2());
                return true;
            case R.id.menu_about:
                Toast.makeText(GUI.this, "Version: " + BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createDatabase(){
        dataBaseManager = new DataBaseManager(openOrCreateDatabase("Game",MODE_PRIVATE,null),this);
        dataBaseManager.createDatabase();
    }

    private void createButtons(){
        roundScore = findViewById(R.id.roundPoints);
        roundScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tichuCounter.checkScore(currentScore1,currentScore2);
                setScore();
            }
        });

        tichu1 = findViewById(R.id.tichu1);
        tichu1.setBackgroundResource(android.R.drawable.btn_default);
        tichu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamTichu1==0){
                    teamTichu1 = 1;
                    tichu1.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                }else{
                    teamTichu1 = 0;
                    tichu1.getBackground().clearColorFilter();
                }
            }
        });

        grandTichu1 = findViewById(R.id.grandTichu1);
        grandTichu1.setBackgroundResource(android.R.drawable.btn_default);
        grandTichu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamGrandTichu1==0){
                    teamGrandTichu1 = 1;
                    grandTichu1.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                }else{
                    teamGrandTichu1 = 0;
                    grandTichu1.getBackground().clearColorFilter();
                }
            }
        });

        tichu2 = findViewById(R.id.tichu2);
        tichu2.setBackgroundResource(android.R.drawable.btn_default);
        tichu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamTichu2==0){
                    teamTichu2 = 1;
                    tichu2.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                }
                else{
                    teamTichu2 = 0;
                    tichu2.getBackground().clearColorFilter();
                }
            }
        });

        grandTichu2 = findViewById(R.id.grandTichu2);
        grandTichu2.setBackgroundResource(android.R.drawable.btn_default);
        grandTichu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamGrandTichu2==0){
                    teamGrandTichu2 = 1;
                    grandTichu2.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                }else{
                    teamGrandTichu2 = 0;
                    grandTichu2.getBackground().clearColorFilter();
                }
            }
        });
    }

    private void createEditText() {
        TextScore1 =  findViewById(R.id.score1EditText);
        currentScore1 = findViewById(R.id.currentScore1);
        currentScore2 = findViewById(R.id.currentScore2);
        TextScore2 =  findViewById(R.id.score2EditText);
    }

    private void createCheckBox(){
        tichuCheck1 = findViewById(R.id.tichuCheck1);
        grandTichuCheck1 = findViewById(R.id.grandTichuCheck1);
        tichuCheck2 = findViewById(R.id.tichuCheck2);
        grandTichuCheck2 = findViewById(R.id.grandTichuCheck2);
    }

    private void playGame(){
        tichuCounter = new TichuCounter(GUI.this);
        tichuCounter.isWinner();
    }

    public void setScore() {
        int flag = tichuCounter.checkTichuStatus(teamTichu1,tichuCheck1,grandTichu1,teamGrandTichu1,grandTichuCheck1,teamTichu2,tichuCheck2,grandTichu2,teamGrandTichu2,grandTichuCheck2,currentScore1,currentScore2);
        if(flag == 1){
            if(error==0){
                TextScore1.setText(String.valueOf(tichuCounter.getScoreTeam1()));
                TextScore2.setText(String.valueOf(tichuCounter.getScoreTeam2()));
                scoreArray[i]=String.valueOf(tichuCounter.getScoreTeam1());
                scoreArray[++i]=String.valueOf(tichuCounter.getScoreTeam2());
                i++;
                dataBaseManager.saveRoundScore(tichuCounter.getScoreTeam1(),tichuCounter.getScoreTeam2());
                clear();
            }else {
                error = 0;
            }
        }else{
            clear();
        }
    }

    private void clearScore() {
        DataBaseManager.start=0;
        score1=0;
        score2=0;

        TextScore1.setText(String.valueOf(""));
        TextScore2.setText(String.valueOf(""));

        teamTichu1=0;
        teamGrandTichu1=0;
        tichuCheck1.setChecked(false);
        grandTichuCheck1.setChecked(false);

        teamTichu2=0;
        teamGrandTichu2=0;
        tichuCheck2.setChecked(false);
        grandTichuCheck2.setChecked(false);

        tichu1.getBackground().clearColorFilter();
        tichu2.getBackground().clearColorFilter();
        grandTichu1.getBackground().clearColorFilter();
        grandTichu2.getBackground().clearColorFilter();
        currentScore1.setText(String.valueOf(""));
        currentScore2.setText(String.valueOf(""));
        playGame();

    }

    private void clear(){
        teamTichu1=0;
        teamGrandTichu1=0;
        tichuCheck1.setChecked(false);
        grandTichuCheck1.setChecked(false);

        teamTichu2=0;
        teamGrandTichu2=0;
        tichuCheck2.setChecked(false);
        grandTichuCheck2.setChecked(false);

        tichu1.getBackground().clearColorFilter();
        tichu2.getBackground().clearColorFilter();
        grandTichu1.getBackground().clearColorFilter();
        grandTichu2.getBackground().clearColorFilter();
        currentScore1.setText(String.valueOf(""));
        currentScore2.setText(String.valueOf(""));
    }
}