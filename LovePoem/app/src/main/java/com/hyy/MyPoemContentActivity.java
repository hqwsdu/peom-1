package com.hyy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyPoemContentActivity extends AppCompatActivity {

    private TextView tvMyPoem;
    private Button btDelete;
    private Button btSpeak;
    private MyPoemBean bean ;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_poem_content);
        tvMyPoem = findViewById(R.id.tv_poem);
        btDelete = findViewById(R.id.bt_delete);
        Intent intent = getIntent();
        bean = new MyPoemBean();
        bean.setTitle(intent.getStringExtra("title"));
        bean.setWriter(intent.getStringExtra("writer"));
        bean.setContent(intent.getStringExtra("content"));
        bean.setId(intent.getIntExtra("id",0));
        tvMyPoem.setText("《"+bean.getTitle()+"》"+"\n\n"+bean.getWriter()+"\n\n"+bean.getContent());
        dbHelper = new DBHelper(getApplicationContext());

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.delete(DBHelper.TABLE_NAME_MY_POEM,bean.getId());
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btSpeak = findViewById(R.id.bt_speak);
        btSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String content = bean.getTitle()+","+bean.getWriter()+","+bean.getContent();
               MainMenuActivity.speak(content);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainMenuActivity.stopSpeaking();
    }
}
