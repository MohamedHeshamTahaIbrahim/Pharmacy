package com.pharmacy.pharmacy.Rochta;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.pharmacy.pharmacy.R;

/**
 * Created by Mohamed Hesham on 2017-05-09.
 */

public class WriteRochtaScreen extends Activity {
    EditText notes;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_rochta);
        notes=(EditText)findViewById(R.id.notes);
    }
}
