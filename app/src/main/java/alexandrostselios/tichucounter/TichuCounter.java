package alexandrostselios.tichucounter;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class TichuCounter {
    private int score1=0;
    private int score2=0;
    private int error=0;
    private boolean win = false;

    private EditText currentScore1;
    private EditText currentScore2;

    Context context;

    public TichuCounter(Context mainActivity){
        this.context=mainActivity;
    }

    public void checkScore(EditText currentScore1, EditText currentScore2){
        if(currentScore1!=null && currentScore2!=null) {
            int counter = 0;
            if(!currentScore1.getText().toString().equals("") && !currentScore2.getText().toString().equals("")){
                if((Integer.parseInt(currentScore1.getText().toString())+(Integer.parseInt(currentScore2.getText().toString()) )) >100
                        || (Integer.parseInt(currentScore1.getText().toString())+(Integer.parseInt(currentScore2.getText().toString()) )) < 100){
                    Toast.makeText(context, "Score of both Teams is WRONG", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
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
                if (Integer.parseInt(currentScore1.getText().toString()) + Integer.parseInt(currentScore2.getText().toString()) == 100) {
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
        error=1;
        setScoreTeam1(score1);
        setScoreTeam2(score2);
    }

    public void setScoreTeam1(int score1){
        this.score1=score1;
    }

    public void setScoreTeam2(int score2){
        this.score2=score2;
    }

    public int getScoreTeam1(){
        return score1;
    }

    public int getScoreTeam2(){
        return score2;
    }

    public boolean isWinner(){
        if(win){
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

    public int checkTichuStatus(int teamTichu1, CheckBox tichuCheck1, Button grandTichu1, int teamGrandTichu1, CheckBox grandTichuCheck1, int teamTichu2, CheckBox tichuCheck2, Button grandTichu2, int teamGrandTichu2, CheckBox grandTichuCheck2, EditText currentScore1, EditText currentScore2){
        if(error==1) {
            error=0;
            if(!currentScore1.getText().toString().equals("") && !currentScore1.getText().toString().equals("")) {
                if ((teamTichu1 == 1 && teamGrandTichu1 == 1 && tichuCheck1.isChecked() && grandTichuCheck1.isChecked())
                        || (teamTichu1 == 1 && teamTichu2 == 1 && tichuCheck1.isChecked() && tichuCheck2.isChecked())
                        || (teamTichu1 == 1 && teamGrandTichu2 == 1 && tichuCheck1.isChecked() && grandTichuCheck2.isChecked())
                        || (teamTichu2 == 1 && teamGrandTichu2 == 1 && tichuCheck2.isChecked() && grandTichuCheck2.isChecked())
                        || (teamGrandTichu1 == 1 && teamTichu2 == 1 && grandTichuCheck1.isChecked() && tichuCheck2.isChecked())
                        || (teamGrandTichu1 == 1 && teamGrandTichu2 == 1 && grandTichuCheck1.isChecked() && grandTichuCheck2.isChecked())
                        || (teamGrandTichu1 == 1 && teamTichu2 == 1 && grandTichuCheck1.isChecked() && tichuCheck2.isChecked() &&
                            teamTichu1 == 1 && teamTichu2 == 1 && tichuCheck1.isChecked() && tichuCheck2.isChecked())
                        ||(teamTichu1 == 1 && teamTichu2 == 1 && teamTichu2 == 1 && teamGrandTichu2 == 1 )) {
                    score1 -= Integer.parseInt(currentScore1.getText().toString());
                    score2 -= Integer.parseInt(currentScore2.getText().toString());
                    Toast.makeText(context.getApplicationContext(), "Check Tichu/Grand", Toast.LENGTH_SHORT).show();
                    return 0;
                }
                if (teamTichu1 == 1 && tichuCheck1.isChecked()) {
                    score1 += 100;
                } else if (teamTichu1 == 1 && !tichuCheck1.isChecked()) {
                    score1 -= 100;
                }
                if (teamGrandTichu1 == 1 && grandTichuCheck1.isChecked()) {
                    score1 += 200;
                } else if (teamGrandTichu1 == 1 && !grandTichuCheck1.isChecked()) {
                    score1 -= 200;
                }
                if (teamTichu2 == 1 && tichuCheck2.isChecked()) {
                    score2 += 100;
                } else if (teamTichu2 == 1 && !tichuCheck2.isChecked()) {
                    score2 -= 100;
                }
                if (teamGrandTichu2 == 1 && grandTichuCheck2.isChecked()) {
                    score2 += 200;
                } else if (teamGrandTichu2 == 1 && !grandTichuCheck2.isChecked()) {
                    score2 -= 200;
                }
                return 1;
            }
            return 0;
        }
        return 0;
    }
}