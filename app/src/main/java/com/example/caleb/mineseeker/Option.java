package com.example.caleb.mineseeker;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import static com.example.caleb.mineseeker.R.id.zombieNum;

public class Option extends AppCompatActivity {
    Spinner boardSize_spinner;
    Spinner zombieNum_spinner;
    String[] boardSizes;
    String[] zombieNums;
    Intent game_Size_Data = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        setUpBoardSizeSpinner();
        setUpZombieNumSpinner();
        setResult(Menu.RESULT_OK,game_Size_Data);
    }

    private void setUpZombieNumSpinner() {
        zombieNum_spinner = (Spinner) findViewById(zombieNum);
        Resources resources = getResources();
        zombieNums = resources.getStringArray(R.array.zombieNum);
        zombieNum_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, zombieNums));
        zombieNum_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //transfer zombieNum data into menu activity
                int zombieNum = 6;
                if (position == 0)
                    zombieNum = 6;
                if (position == 1)
                    zombieNum = 10;
                if (position == 2)
                    zombieNum = 15;
                if (position == 3)
                    zombieNum = 20;
                game_Size_Data.putExtra("zombieNum_data", zombieNum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUpBoardSizeSpinner() {
        boardSize_spinner = (Spinner) findViewById(R.id.boardSize);
        Resources resources = getResources();
        boardSizes = resources.getStringArray(R.array.boradSize);
        boardSize_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, boardSizes));
        boardSize_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //extract data from options
                int rowSize = 4;
                int colSize = 6;
                if (position == 0) {
                    rowSize = 4;
                    colSize = 6;
                }
                if (position == 1) {
                    rowSize = 5;
                    colSize = 10;
                }
                if (position == 2) {
                    rowSize = 6;
                    colSize = 15;
                }
                //pass data back to menu
                game_Size_Data.putExtra("rowSize_data", rowSize);
                game_Size_Data.putExtra("colSize_data", colSize);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, Option.class);
        return intent;
    }
}