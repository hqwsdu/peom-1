package com.hyy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WritePoemActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etTitle;
    private EditText etWriter;
    private EditText etContent;
    private Button btSubmit;
    private Button btMyPoem;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_poem);

        etTitle = findViewById(R.id.et_title);
        etWriter = findViewById(R.id.et_writer);
        etContent = findViewById(R.id.et_content);
        btSubmit = findViewById(R.id.bt_submit);
        btMyPoem = findViewById(R.id.bt_my_poem);
        btSubmit.setOnClickListener(this);
        btMyPoem.setOnClickListener(this);



        dbHelper = new DBHelper(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_submit:
                submit();
                break;
            case R.id.bt_my_poem:
                Intent intent = new Intent(WritePoemActivity.this,MyPoemListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void submit()
    {
       String title = etTitle.getText().toString();
       String writer = etWriter.getText().toString();
       String content = etContent.getText().toString();
       if(title.isEmpty())
       {
           Toast.makeText(this,"请输入标题",Toast.LENGTH_SHORT).show();
       }
       else if(writer.isEmpty())
       {
           Toast.makeText(this,"请输入作者",Toast.LENGTH_SHORT).show();
       }
       else if(content.isEmpty())
       {
           Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT).show();
       }
       else
       {
           MyPoemBean bean = new MyPoemBean();
           bean.setTitle(title);
           bean.setWriter(writer);
           bean.setContent(content);
           dbHelper.add(bean);
           Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
           etTitle.setText("");
           etWriter.setText("");
           etContent.setText("");
       }

    }

}
