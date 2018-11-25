package com.example.alexa.tichucounter;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class TichuCounter {
    private int score1=0;
    private int score2=0;
    private int error=0;
    private boolean win = false;

    Context context;

    public TichuCounter(Context context){
        this.context=context;
    }

    public void checkScore(EditText currentScore1, EditText currentScore2){
        if(currentScore1!=null && currentScore2!=null) {
            int counter = 0;
            if (!currentScore1.getText().toString().equals("")) {
                if (currentScore1.isFocused()) {
                    if ((Integer.parseInt(currentScore1.getText().toString()) % 5) != 0 && (Integer.parseInt(currentScore1.getText().toString()) % 5) != 5) {
                        Toast.makeText(context, "Score of team 1 is WRONG", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ((Integer.parseInt(currentScore1.getText().toString()) > 125) || (Integer.parseInt(currentScore1.getText().toString()) < -25)) {
                        Toast.makeText(context, "Score of team 1 is WRONG", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                currentScore2.setText(String.valueOf(100 - Integer.parseInt(currentScore1.getText().toString())));
                counter++;
            }
            if (!currentScore2.getText().toString().equals("")) {
                if (currentScore2.isFocused()) {
                    if ((Integer.parseInt(currentScore2.getText().toString()) % 5) != 0 && (Integer.parseInt(currentScore2.getText().toString()) % 5) != 5) {
                        Toast.makeText(context, "Score of team 2 is WRONG", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ((Integer.parseInt(currentScore2.getText().toString()) > 125) || (Integer.parseInt(currentScore2.getText().toString()) < -25)) {
                        Toast.makeText(context, "Score of team 2 is WRONG", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                currentScore1.setText(String.valueOf(100 - Integer.parseInt(currentScore2.getText().toString())));
                counter++;
            }
            if (counter >= 1) {
                if (Integer.parseInt(currentScore1.getText().toString()) + Integer.parseInt(currentScore2.getText().toString()) <= 100) {
                    score1 += Integer.parseInt(currentScore1.getText().toString());
                    score2 += Integer.parseInt(currentScore2.getText().toString());
                } else {
                    Toast.makeText(context, "Score Above 100 points", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Please give points", Toast.LENGTH_SHORT).show();
            }
            checkWinner();
        }
    }

    public int getScoreteam1(){
        return score1;
    }

    public int getScoreteam2(){
        return score2;
    }

    public boolean isWinner(){
        if(win){
            Log.i("winner","+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return true;
        }
        return false;
    }

    private void checkWinner(){
        if(score1>=1000 && score2<1000){
            win = true;
            Toast.makeText(context, "Team 1 won the game", Toast.LENGTH_SHORT).show();
        }else if(score2>=1000 && score1<1000){
            win = true;
            Toast.makeText(context, "Team 2 won the game", Toast.LENGTH_SHORT).show();
        }else if(score1>=1000 && score2>=1000){
            if(score1>score2){
                Toast.makeText(context, "Team 1 won the game", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Team 2 won the game", Toast.LENGTH_SHORT).show();
            }
            win = true;
        }
    }
}
