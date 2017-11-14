package com.parfoismeng.decimaltextview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;

import com.parfoismeng.decimaltextview.R;
import com.parfoismeng.decimaltextviewlib.widget.ParfoisDecimalEditText;

/**
 * <pre>
 *     author : parfoismeng
 *     e-mail : youshi520000@163.com
 *     time   : 2017/11/13
 *     desc   : do_
 *     version: 1.0
 * </pre>
 */
public class EditTextActivity extends AppCompatActivity {
    SwitchCompat switchSymbol;
    SwitchCompat switchCommas;
    ParfoisDecimalEditText etDecimalFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);

        switchSymbol = findViewById(R.id.switchSymbol);
        switchCommas = findViewById(R.id.switchCommas);
        etDecimalFormat = findViewById(R.id.etDecimalFormat);

        switchSymbol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etDecimalFormat.setShowSymbol(isChecked);
        });
        switchCommas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etDecimalFormat.setShowCommas(isChecked);
        });
    }
}