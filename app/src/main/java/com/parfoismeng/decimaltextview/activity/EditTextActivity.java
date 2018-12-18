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
import com.parfoismeng.decimaltextviewlib.widget.DecimalEditText;

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
    DecimalEditText etDecimalFormat;

    Spinner spinnerSymbol;
    SwitchCompat switchSymbol;
    SwitchCompat switchCommas;
    SwitchCompat switchFillZero;

    RadioGroup rgSymbolSize;
    Button btnSymbolSizeClean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);

        etDecimalFormat = findViewById(R.id.etDecimalFormat);

        spinnerSymbol = findViewById(R.id.spinnerSymbol);
        switchSymbol = findViewById(R.id.switchSymbol);
        switchCommas = findViewById(R.id.switchCommas);
        switchFillZero = findViewById(R.id.switchFillZero);

        rgSymbolSize = findViewById(R.id.rgSymbolSize);
        btnSymbolSizeClean = findViewById(R.id.btnSymbolSizeClean);

        spinnerSymbol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 此回调初始会调用一次，详情请自行搜索Spinner相关问题
                String[] symbols = getResources().getStringArray(R.array.symbols);
                etDecimalFormat.setSymbol(symbols[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        switchSymbol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etDecimalFormat.setShowSymbol(isChecked);
        });
        switchCommas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etDecimalFormat.setShowCommas(isChecked);
        });
        switchFillZero.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etDecimalFormat.setDecimalFill(isChecked);
        });

        rgSymbolSize.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbSymbolSize12:
                    etDecimalFormat.setSymbolSize(12);
                    break;
                case R.id.rbSymbolSize20:
                    etDecimalFormat.setSymbolSize(20);
                    break;
                case R.id.rbSymbolSize32:
                    etDecimalFormat.setSymbolSize(32);
                    break;
            }
        });
        btnSymbolSizeClean.setOnClickListener(v -> {
            rgSymbolSize.clearCheck();
            etDecimalFormat.setSymbolSize(0);
        });
    }
}