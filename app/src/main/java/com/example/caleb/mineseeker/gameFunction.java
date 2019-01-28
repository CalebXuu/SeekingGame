package com.example.caleb.mineseeker;

import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class gameFunction {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int[][] getMatrix(int rowNum, int columnNum, int zombieNum) {
        int max = rowNum * columnNum;
        int array1[] = new int[max];
        for (int i = 0; i < max; i++) {
            array1[i] = 0;
        }
        int[] RandomNum = randomCommon(0, max, zombieNum);
        for (int i = 0; i < zombieNum; i++) {
            array1[RandomNum[i]] = 1;
        }
        int array2[][] = new int[rowNum][columnNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                array2[i][j] = array1[columnNum * i + j];
            }
        }
        return array2;
    }

    public int getZombieNum(int rowNum, int columnNum, int[][] Matrix, int rowNumMax, int columnNumMax) {
        int zombieNum = 0;
        for (int i = 0; i < rowNumMax; i++) {
            zombieNum = zombieNum + Matrix[i][columnNum];
        }
        for (int j = 0; j < columnNumMax; j++) {
            zombieNum = zombieNum + Matrix[rowNum][j];
        }
        return zombieNum;
    }

    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    public int[][] buildCheckOpenMatrix(int rowNum, int columnNum, int[][] Matrix) {
        int array3[][] = new int[rowNum][columnNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                array3[i][j] = 0;
            }
        }


        return array3;
    }
}
