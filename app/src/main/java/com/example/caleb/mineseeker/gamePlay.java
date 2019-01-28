package com.example.caleb.mineseeker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class gamePlay extends AppCompatActivity {
    private  int NUM_ROWS;
    private  int NUM_COLS;
    private  int zombieCount;
    private  int zombieFound=0;
    private  int scanUsed=0;
    private  int width=0;
    private  int height=0;
    private  TextView text1;
    private  TextView text2;
    private TableRow tableRow;
    private SoundPool sp;
    private SoundPool sp2;
    private SoundPool sp3;
    private int zombieNotFind;
    private int zombieFind;
    private int winSound;
    private int[][] Matrix;
    private int[][] CheckMatrix;
    private gameFunction game= new gameFunction();
    Button buttons[][];
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extractDataFromIntent();
        setContentView(R.layout.activity_game_play);
        sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        zombieFind = sp.load(this, R.raw.zombiefound, 1);
        sp2= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        zombieNotFind =sp2.load(this, R.raw.notzombiefound, 1);
        sp3= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        winSound =sp3.load(this, R.raw.winsound, 1);


        buttons=new Button[NUM_ROWS][NUM_COLS];
        //
        Matrix=game.getMatrix(NUM_ROWS,NUM_COLS,zombieCount);
        CheckMatrix=game.buildCheckOpenMatrix(NUM_ROWS,NUM_COLS,Matrix);//check if button is clicked
        updateUi();
        populateButtons();
        setResult(Menu.RESULT_OK);


    }
    private void updateUi()
    {
        text1=(TextView)findViewById(R.id.textFound);
        text1.setText("Hunter "+ zombieFound + " of "+zombieCount+" Zombies");
        text2=(TextView)findViewById(R.id.textMove);
        text2.setText("# Scan used: "+scanUsed);
    }

    private void extractDataFromIntent() {
        Intent intent=getIntent();
        NUM_ROWS=intent.getIntExtra("rowNum",4);
        NUM_COLS=intent.getIntExtra("columnNum",6);
        zombieCount=intent.getIntExtra("zombieNum",6);

    }

    private void populateButtons() {
        TableLayout table=(TableLayout) findViewById(R.id.tableForButtons);
        for(int row=0;row<NUM_ROWS;row++){
            tableRow=new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int col=0;col<NUM_COLS;col++){
                final int Final_COL=col;
                final int Final_ROW=row;
                Button button=new Button(this);
                button.setBackgroundResource(R.drawable.blankimg);

                width=button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                height=button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
                button.setBackgroundResource(R.drawable.blankimg);

                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                //make text not clip on button
                button.setPadding(0,0,0,0);
                //test
                tableRow.addView(button);
                buttons[row][col]=button;
                button.setOnClickListener(new View.OnClickListener(){


                    @Override
                    public void onClick(View v) {
                        if (Matrix[Final_ROW][Final_COL]==0){//if button at this place is not a zombie
                            if(CheckMatrix[Final_ROW][Final_COL]==0) {//if button is not clicked
                                sp2.play(zombieNotFind, 1, 1, 0, 0, 1);
                                buttons[Final_ROW][Final_COL].setTextColor(Color.parseColor("#ff0000"));
                                buttons[Final_ROW][Final_COL].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                                buttons[Final_ROW][Final_COL].setText("" + game.getZombieNum(Final_ROW, Final_COL, Matrix, NUM_ROWS, NUM_COLS));
                                CheckMatrix[Final_ROW][Final_COL] = 1;//button now becomes "clicked" status
                                scanUsed++;//the action of counting zombie numbers was recorded
                                text2.setText("# Scan used: "+scanUsed);
                            }
                        }
                        else {
                            gridButtonClicked( Final_ROW,Final_COL);//button here is zombie and zombie image appeared
                            sp.play(zombieFind, 1, 1, 0, 0, 1);
                            Matrix[Final_ROW][Final_COL]=0;//button now becomes non-zombie and not clicked status
                            zombieFound++;
                            text1.setText("Hunter "+zombieFound+" of "+zombieCount+ " Zombies");
                            for(int i=0;i<NUM_ROWS;i++)//
                            {
                                if(CheckMatrix[i][Final_COL]==1)//if buttons along the column of current button were clicked and not a zombie button
                                {
                                    //show hidden zombie numbers on the buttons along the current button
                                    buttons[i][Final_COL].setText(""+game.getZombieNum(i,Final_COL,Matrix,NUM_ROWS,NUM_COLS));
                                    //Toast.makeText(gamePlay.this, "Num"+(i*NUM_COLS+Final_COL)+" "+CheckMatrix[i][Final_COL], Toast.LENGTH_SHORT).show();
                                }
                            }
                            for(int j=0;j<NUM_COLS;j++)
                            {
                                if(CheckMatrix[Final_ROW][j]==1)
                                {
                                    buttons[Final_ROW][j].setText(""+game.getZombieNum(Final_ROW,j,Matrix,NUM_ROWS,NUM_COLS));
                                    //Toast.makeText(gamePlay.this, "Num"+(Final_ROW*NUM_COLS+j)+" "+CheckMatrix[Final_ROW][j], Toast.LENGTH_SHORT).show();
                                }
                            }
                            //check if total zombies are found, congratulation alert!
                            if(zombieFound==zombieCount){
                                sp3.play(winSound, 1, 1, 0, 0, 1);
                                FragmentManager manager=getSupportFragmentManager();
                                CongratulationsFragment dialog=new CongratulationsFragment();
                                dialog.show(manager,"CongratulationDialog");
                            }
                        }
                    }
                });
            }
        }

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    private void gridButtonClicked(int row, int col) {
        Button button1=buttons[row][col];
        lockButtonSizes(width,height);
        button1.setBackgroundResource(R.drawable.zombieimg);
    }

    private void lockButtonSizes(int buttonWidth,int buttonHeight) {
        for(int row=0;row<NUM_ROWS;row++){
            for(int col=0;col<NUM_COLS;col++){
                Button button2=buttons[row][col];

                int width1=buttonWidth;
                button2.setMinWidth(width1);
                button2.setMaxWidth(width1);

                int height1=buttonHeight;
                button2.setMinHeight(height1);
                button2.setMaxHeight(height1);
            }
        }
    }
    public static Intent makeIntent(Context context, int rowNum, int columnNum, int mineNum) {
        Intent intent = new Intent(context,gamePlay.class);
        intent.putExtra("rowNum", rowNum);
        intent.putExtra("columnNum", columnNum);
        intent.putExtra("zombieNum",mineNum);
        return intent;
    }
}
