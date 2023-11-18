package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class HistoryActivity extends AppCompatActivity {
    TextView history;
    TextView back;
    ImageView clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        history = findViewById(R.id.history);

        Intent getHistory = getIntent();
        String value = getHistory.getStringExtra("key");
        history.setText(value);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        clear = findViewById(R.id.clear);
        if(!value.isEmpty()){
            clear.setOnClickListener(v -> {
                Intent remove = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(remove);
            });
        }
    }
   public void onBackPressed() {
        super.onBackPressed();
    }
}