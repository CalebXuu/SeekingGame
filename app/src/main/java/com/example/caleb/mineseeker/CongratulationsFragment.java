package com.example.caleb.mineseeker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class CongratulationsFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Create the View to show
        View v= LayoutInflater.from(getActivity())
                .inflate(R.layout.congratulations_layout,null);



        //Create a button Listener
        DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        };

        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Congratulations!")
                .setView(v)
                .setPositiveButton(android.R.string.ok,listener)
                .create();

    }
}