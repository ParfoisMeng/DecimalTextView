package com.parfoismeng.decimaltextview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
    ParfoisDecimalTextView tvDecimalFormat;

    Spinner spinnerSymbol;
    SwitchCompat switchSymbol;
    SwitchCompat switchCommas;
    SwitchCompat switchFillZero;

    RadioGroup rgSymbolSize;
    Button btnSymbolSizeClean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);

        tvDecimalFormat = findViewById(R.id.tvDecimalFormat);

        spinnerSymbol = findViewById(R.id.spinnerSymbol);
        switchSymbol = findViewById(R.id.switchSymbol);
        switchCommas = findViewById(R.id.switchCommas);
        switchFillZero = findViewById(R.id.switchFillZero);

        rgSymbolSize = findViewById(R.id.rgSymbolSize);
        btnSymbolSizeClean = findViewById(R.id.btnSymbolSizeClean);

        spinnerSymbol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] symbols = getResources().getStringArray(R.array.symbols);
                tvDecimalFormat.setSymbol(symbols[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        switchSymbol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tvDecimalFormat.setShowSymbol(isChecked);
        });
        switchCommas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tvDecimalFormat.setShowCommas(isChecked);
        });
        switchFillZero.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tvDecimalFormat.setDecimalFill(isChecked);
        });

        rgSymbolSize.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbSymbolSize12:
                    tvDecimalFormat.setSymbolSize(12);
                    break;
                case R.id.rbSymbolSize20:
                    tvDecimalFormat.setSymbolSize(20);
                    break;
                case R.id.rbSymbolSize32:
                    tvDecimalFormat.setSymbolSize(32);
                    break;
            }
        });
        btnSymbolSizeClean.setOnClickListener(v -> {
            rgSymbolSize.clearCheck();
            tvDecimalFormat.setSymbolSize(0);
        });
    }
}