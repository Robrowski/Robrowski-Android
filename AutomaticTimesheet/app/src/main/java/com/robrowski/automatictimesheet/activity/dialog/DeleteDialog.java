package com.robrowski.automatictimesheet.activity.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Robrowski on 6/21/2015.
 */
public class DeleteDialog implements DialogInterface.OnClickListener {


    private DeleteDialogListener mDeleteDialogListener;

    private DeleteDialog(DeleteDialogListener ddl) {
        mDeleteDialogListener = ddl;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                mDeleteDialogListener.deletionConfirmed();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                // Do nothing
                break;
        }
    }

    /** Show a dialog box and alert the call back */
    public static void showDeleteDialog(Context c, DeleteDialogListener mDeleteDialogListener) {
        DeleteDialog dd = new DeleteDialog(mDeleteDialogListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage("Permanently delete this?")
                .setPositiveButton("Yes", dd)
                .setNegativeButton("No",  dd).show();
    }

    public interface DeleteDialogListener {
        void deletionConfirmed();
    }

}
