package com.asm.brainstorm;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    GridLayout gridLayout;
    Button goButton;
    TextView timer;
    TextView calc;//The sum
    ArrayList<Integer> array;//A array created with random answers having 1 correct answer
    int arrayCount;
    ArrayList <Button>ans;
    int ansTag;//tag of the correct button in grid
    TextView score;
    TextView result;
    ImageView playAgain;


//Sets everything to play the game
    public void go(View view){
        Button button=(Button) view;
        button.setVisibility(View.GONE);

        linearLayout.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        timerFunc();
        setGrid();
        score.setText("0/0");

    }
//Timer and its effect on the game
    public void timerFunc(){

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                String timerText = (l / 1000) + "s";
                timer.setText(timerText);
            }

            @Override
            public void onFinish() {
                result.setText(evaluation());
                gridLayout.setVisibility(View.GONE);
                playAgain.setVisibility(View.VISIBLE);

            }
        }.start();


    }

//Setting up the 4 buttons of the grid with an array having only one correct answer at a random index
    public void setGrid(){


        array= new ArrayList<>(Arrays.asList(0, 0, 0, 0));


        int randomA= new Random().nextInt(51);
        int randomB= new Random().nextInt(51);
        int total=randomA+randomB;
        String calcText=randomA+"+"+randomB;
        calc.setText(calcText);



        arrayCount=new Random().nextInt(4);
        for(int i=0;i<4;i++){
            if(i==arrayCount){
                array.set(i,total);
            }
            else {
                int extra=new Random().nextInt(101);
                //Loop used so that the answer wont repeat in other indexes
                while (extra==total){
                    extra=new Random().nextInt(101);
                }
                array.set(i,extra);
            }
        }





        for (int i=0;i<4;i++){

            ans.get(i).setText(array.get(i).toString());

            if(i==arrayCount){
                ansTag=Integer.parseInt(ans.get(i).getTag().toString());
            }
        }
    }
//Sets what happens after clicking a button in the grid while the game is running
    public void clickAns(View view){


        Button clickedButton=(Button) view;
        String[] scoreText =score.getText().toString().split("/");
        int correct=Integer.parseInt(scoreText[0]);
        int totalAnswered=Integer.parseInt(scoreText[1])+1;
        result.setVisibility(View.VISIBLE);

        if(Integer.parseInt(clickedButton.getTag().toString())==ansTag){
            correct=correct+1;
            result.setText("Correct!!");

        }
        else {
            result.setText("Wrong!!");
        }
        score.setText(correct+"/"+totalAnswered);
        setGrid();
    }


//Sets what happens after the play again view is clicked
    public void setPlayAgain(View view){


        playAgain.setVisibility(View.GONE);
        result.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        go(goButton);


    }

// It evaluates the score and tells the player his stand
    public String evaluation(){


        String[] scoreText =score.getText().toString().split("/");
        int correct=Integer.parseInt(scoreText[0]);
        int totalAnswered=Integer.parseInt(scoreText[1]);
        String eval;

        float ratio=correct*100/totalAnswered;
        if(ratio>=90 && correct>=17){
            eval="Overall Excellent";
        }
        else if(ratio>=70 &&  correct>=12&& correct<17){
            eval="Very Good and Time Usage is also Better";

        }
        else if(ratio>=50 &&  correct>=8&& correct<12){

            eval="You Have to Go a Long Way";
        }
        else{
            eval="It is Not Expected From You!!";
        }
        return eval;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //All variable are initialized here
        linearLayout=findViewById(R.id.linearLayout);
        gridLayout=findViewById(R.id.gridLayout);
        timer=findViewById(R.id.timer);
        calc=findViewById(R.id.sum);
        ans= new ArrayList<>(Arrays.asList(findViewById(R.id.ans1),
                findViewById(R.id.ans2), findViewById(R.id.ans3), findViewById(R.id.ans4)));
        score=findViewById(R.id.score);
        result=findViewById(R.id.result);
        playAgain=findViewById(R.id.playAgain);
        goButton=findViewById(R.id.button);




    }
}