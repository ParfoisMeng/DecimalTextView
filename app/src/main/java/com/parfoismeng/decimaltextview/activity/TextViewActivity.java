package com.parfoismeng.decimaltextview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;

import com.parfoismeng.decimaltextview.R;
import com.parfoismeng.decimaltextviewlib.widget.ParfoisDecimalTextView;

/**
 * <pre>
 *     author : parfoismeng
 *     e-mail : youshi520000@163.com
 *     time   : 2017/11/13
 *     desc   : do_
 *     version: 1.0
 * </pre>
 */
public class TextViewActivity extends AppCompatActivity {
    SwitchCompat switchSymbol;
    SwitchCompat switchCommas;
    SwitchCompat switchFillZero;
    ParfoisDecimalTextView tvDecimalFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);

        switchSymbol = findViewById(R.id.switchSymbol);
        switchCommas = findViewById(R.id.switchCommas);
        switchFillZero = findViewById(R.id.switchFillZero);
        tvDecimalFormat = findViewById(R.id.tvDecimalFormat);

        switchSymbol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tvDecimalFormat.setShowSymbol(isChecked);
        });
        switchCommas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tvDecimalFormat.setShowCommas(isChecked);
        });
        switchFillZero.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tvDecimalFormat.setDecimalFill(isChecked);
        });
    }
}