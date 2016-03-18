package com.ppalumbo_a4.peter.ppalumbo_a4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by peter on 3/18/16.
 */
public class GameOverDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.game_over);

        builder.setPositiveButton(R.string.start_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(this, GameActivity.class);
//                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent exit = new Intent(getContext(), WelcomeActivity.class);
//                startActivity(exit);
            }
        });
        return builder.create();
    }
}
