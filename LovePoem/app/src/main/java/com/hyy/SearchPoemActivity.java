package com.hyy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SearchPoemActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etKeyWord;
    private Button btSearch;
    private TextView tvSearchResult;
    private TextView tvTips;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_poem);
        etKeyWord = findViewById(R.id.et_key_word);
        btSearch = findViewById(R.id.bt_search);
        tvSearchResult = findViewById(R.id.tv_search_result);
        tvTips = findViewById(R.id.tv_tips);
        dbHelper = new DBHelper(getApplicationContext());
        btSearch.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_search:
                search();
                break;
        }
    }

    /**
     * 查询相关操作
     */
    private void search()
    {
        String keyword = etKeyWord.getText().toString();
        if(keyword.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"请输入关键字",Toast.LENGTH_SHORT).show();
        }
        else
        {
            List<PoemBean> poemBeanList = dbHelper.keyWordQuery(DBHelper.FIELD_POEM_CONTENT,keyword);
            if(poemBeanList.size() == 0)
            {
                tvTips.setText("没有相关内容！");
            }
            else
            {
                StringBuilder stringBuilder = new StringBuilder();
                for(PoemBean bean:poemBeanList)
                {
                    stringBuilder.append(bean.getPoemContent());
                }
                tvTips.setText("查找到以下相关内容");
                tvSearchResult.setText(stringBuilder.toString());
            }
        }
    }
}
