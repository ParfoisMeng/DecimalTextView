package com.parfoismeng.decimaltextview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parfoismeng.decimaltextview.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnTextView).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TextViewActivity.class))
        );

        findViewById(R.id.btnEditText).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, EditTextActivity.class))
        );
    }
}