package com.hyy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PoemKnowledgeActivity extends AppCompatActivity {

    private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_knowledge);
        tvContent = findViewById(R.id.tv_content);
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        tvContent.setText(content);
    }
}
