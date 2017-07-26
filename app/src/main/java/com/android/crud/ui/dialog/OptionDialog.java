package com.android.crud.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by EduSPOT on 21/07/2017.
 */

public class OptionDialog extends DialogFragment {

    CharSequence[] optionList = {"Edit", "Delete"};
    OptionDialogListener listener;

    public static OptionDialog newInstance(OptionDialogListener listener) {
        OptionDialog frag = new OptionDialog();
        frag.listener = listener;
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        builder.setTitle("Select Options");
        builder.setItems(optionList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onOptionClickListener(i);
            }
        });

        return builder.create();
    }

    public interface OptionDialogListener {
        void onOptionClickListener(int i);
    }
}
