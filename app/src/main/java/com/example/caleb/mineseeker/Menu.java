package com.example.caleb.mineseeker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    private int rowNum =4;
    private int columnNum =6;
    private int zombieNum =6;
    public static final int REQUEST_CODE_GETMESSAGE = 101;
    public static final int REQUEST_CODE_GETMESSAGE2 = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setupOptionBtn();
        setupHelpBtn();
        setupStartGameBtn();
    }
    private void setupStartGameBtn() {
        Button btn1 = (Button) findViewById(R.id.gameBtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 =gamePlay.makeIntent(Menu.this, rowNum, columnNum,zombieNum);
                startActivityForResult(intent2, REQUEST_CODE_GETMESSAGE2);
            }
        });
    }
    private void setupOptionBtn() {
        Button btn1 = (Button) findViewById(R.id.optionBtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent getOption = Option.makeIntent(Menu.this, rowNum, columnNum,zombieNum);
                Intent getOption = Option.makeIntent(Menu.this);
                startActivityForResult(getOption, REQUEST_CODE_GETMESSAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //get data from option activity
        switch(requestCode) {
            case REQUEST_CODE_GETMESSAGE:
                if (resultCode == Activity.RESULT_OK) {
                    //get Row Size and Column Size and total zombie numbers;may also erase total played times
                    rowNum = data.getIntExtra("rowSize_data", 4);
                    columnNum = data.getIntExtra("colSize_data", 6);
                    zombieNum = data.getIntExtra("zombieNum_data", 6);
                }
        }

    }

    private void setupHelpBtn(){
        Button btn2 = (Button) findViewById(R.id.helpBtn);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(Menu.this, Help.class);
                startActivity(intent2);
            }
        });
    }

}
