package com.example.egemen_godeoglu_proje_15011036;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;

public class TarihSecici extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c= Calendar.getInstance();
        int yil=c.get(Calendar.YEAR);
        int ay=c.get(Calendar.MONTH);
        int gun=c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(),yil,ay,gun);
    }
}

