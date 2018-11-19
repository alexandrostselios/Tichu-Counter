package com.example.alexa.myapplication;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    //private Button setScore = null;
    private TextView roundScore = null;

    private Button tichu1 = null;
    private Button grandTichu1 = null;
    private Button tichu2 = null;
    private Button grandTichu2 = null;

    private EditText TextScore1 = null;
    private EditText currentScore1 = null;
    private EditText TextScore2 = null;
    private EditText currentScore2 = null;

    private CheckBox tichuCheck1 = null;
    private CheckBox grandTichuCheck1 = null;
    private CheckBox tichuCheck2 = null;
    private CheckBox grandTichuCheck2 = null;

    private int teamTichu1 = 0;
    private int teamCheck1 = 0;
    private int teamGrandTichu1 = 0;
    private int score1 = 0;
    private int teamTichu2 = 0;
    private int teamCheck2 = 0;
    private int teamGrandTichu2 = 0;
    private int score2 = 0;

    private int error=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createButtons();
        createEditText();
        createCheckBox();
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
            case R.id.reset_score:
                clearScore();
                return true;
            case R.id.menu_save:
                Toast.makeText(MainActivity.this, "Save is Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_about:
                Toast.makeText(MainActivity.this, "About is Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_version:
                Toast.makeText(MainActivity.this, "Version: 1.0", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearScore() {
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
    }

    private void createButtons(){
/*
        setScore = (Button) findViewById(R.id.setScore);
        setScore.setBackgroundResource(android.R.drawable.btn_default);

        setScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkScore();
            }
        });
*/

        roundScore = (TextView) findViewById(R.id.roundPoints);
        roundScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkScore();
            }
        });

        tichu1 = (Button) findViewById(R.id.tichu1);
        tichu1.setBackgroundResource(android.R.drawable.btn_default);
        tichu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamTichu1==0){
                    teamTichu1 = 1;
                    tichu1.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
                }else{
                    teamTichu1 = 0;
                    tichu1.getBackground().clearColorFilter();
                }
            }
        });

        grandTichu1 = (Button) findViewById(R.id.grandTichu1);
        grandTichu1.setBackgroundResource(android.R.drawable.btn_default);
        grandTichu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamGrandTichu1==0){
                    teamGrandTichu1 = 1;
                    grandTichu1.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
                }else{
                    teamGrandTichu1 = 0;
                    grandTichu1.getBackground().clearColorFilter();
                }
            }
        });

        tichu2 = (Button) findViewById(R.id.tichu2);
        tichu2.setBackgroundResource(android.R.drawable.btn_default);
        tichu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamTichu2==0){
                    teamTichu2 = 1;
                    tichu2.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
                }
                else{
                    teamTichu2 = 0;
                    tichu2.getBackground().clearColorFilter();
                }
            }
        });

        grandTichu2 = (Button) findViewById(R.id.grandTichu2);
        grandTichu2.setBackgroundResource(android.R.drawable.btn_default);
        grandTichu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamGrandTichu2==0){
                    teamGrandTichu2 = 1;
                    grandTichu2.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
                }else{
                    teamGrandTichu2 = 0;
                    grandTichu2.getBackground().clearColorFilter();
                }
            }
        });
    }

    private void createEditText() {
        currentScore1 = (EditText) findViewById(R.id.currentScore1);
        currentScore2 = (EditText) findViewById(R.id.currentScore2);
        TextScore1 = (EditText) findViewById(R.id.score1);
        TextScore2 = (EditText) findViewById(R.id.score2);
    }

    private void createCheckBox(){
        tichuCheck1 = (CheckBox) findViewById(R.id.tichuCheck1);
        grandTichuCheck1 = (CheckBox) findViewById(R.id.grandTichuCheck1);
        tichuCheck2 = (CheckBox) findViewById(R.id.tichuCheck2);
        grandTichuCheck2 = (CheckBox) findViewById(R.id.grandTichuCheck2);
    }

    private void checkScore(){
        int counter=0;
        if(!currentScore1.getText().toString().equals("")){
            if(currentScore1.isFocused()){
                if((Integer.parseInt(currentScore1.getText().toString())%5)!=0 && (Integer.parseInt(currentScore1.getText().toString())%5)!=5){
                    Toast.makeText(getApplicationContext(), "Score of team 1 is WRONG", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((Integer.parseInt(currentScore1.getText().toString()) > 125) || (Integer.parseInt(currentScore1.getText().toString()) < -25)){
                    Toast.makeText(getApplicationContext(), "Score of team 1 is WRONG", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            currentScore2.setText(String.valueOf(100 - Integer.parseInt(currentScore1.getText().toString())));
            counter++;
        }
        if(!currentScore2.getText().toString().equals("")){
            if(currentScore2.isFocused()){
                if((Integer.parseInt(currentScore2.getText().toString())%5)!=0 && (Integer.parseInt(currentScore2.getText().toString())%5)!=5){
                    Toast.makeText(getApplicationContext(), "Score of team 2 is WRONG", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((Integer.parseInt(currentScore2.getText().toString()) > 125) || (Integer.parseInt(currentScore2.getText().toString()) < -25)){
                    Toast.makeText(getApplicationContext(), "Score of team 2 is WRONG", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            currentScore1.setText(String.valueOf(100 - Integer.parseInt(currentScore2.getText().toString())));
            counter++;
        }
        if(counter>=1){
            if (Integer.parseInt(currentScore1.getText().toString()) + Integer.parseInt(currentScore2.getText().toString()) <= 100) {
                score1 += Integer.parseInt(currentScore1.getText().toString());
                score2 += Integer.parseInt(currentScore2.getText().toString());
                setScore();
            } else {
                Toast.makeText(getApplicationContext(), "Score Above 100 points", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Please give points", Toast.LENGTH_SHORT).show();
        }
    }

    private void setScore() {
        checkTichuStatus();
        if(error==0){
            TextScore1.setText(String.valueOf(score1));
            TextScore2.setText(String.valueOf(score2));
            clear();
        }else{
            error=0;
        }
    }

    private void checkTichuStatus(){
        if((teamTichu1==1 && teamGrandTichu1==1 && tichuCheck1.isChecked() && grandTichuCheck1.isChecked())
            || (teamTichu1==1 && teamTichu2==1 && tichuCheck1.isChecked() && tichuCheck2.isChecked())
            || (teamTichu1==1 && teamGrandTichu2==1 && tichuCheck1.isChecked() && grandTichuCheck2.isChecked())
            || (teamTichu2==1 && teamGrandTichu2==1 && tichuCheck2.isChecked() && grandTichuCheck2.isChecked())
            || (teamGrandTichu1==1 && teamTichu2==1 && grandTichuCheck1.isChecked() && tichuCheck2.isChecked())
            || (teamGrandTichu1==1 && teamGrandTichu2==1 && grandTichuCheck1.isChecked() && grandTichuCheck2.isChecked())){

            Toast.makeText(getApplicationContext(), "Check Tichu/Grand", Toast.LENGTH_SHORT).show();
            error=1;
            return;
        }
        if(teamTichu1==1 && tichuCheck1.isChecked()){
            Log.d("null", "Team 1 TICHU");
            score1 += 100;
        }else if(teamTichu1==1 && !tichuCheck1.isChecked()){
            score1 -= 100;
        }
        if(teamGrandTichu1==1 && grandTichuCheck1.isChecked()){
            Log.d("null", "Team 1 GRAND TICHU");
            score1 += 200;
        }else if(teamGrandTichu1==1 && !grandTichuCheck1.isChecked()){
            score1 -= 200;
        }
        if(teamTichu2==1 && tichuCheck2.isChecked()){
            score2 += 100;
        }else if(teamTichu2==1 && !tichuCheck2.isChecked()){
            score2 -= 100;
        }
        if(teamGrandTichu2==1 && grandTichuCheck2.isChecked()){
            score2 += 200;
        }else if(teamGrandTichu2==1 && !grandTichuCheck2.isChecked()){
            score2 -= 200;
        }
        Log.d("null", "score1: "+score1);
        Log.d("null", "score2: "+score2);
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
