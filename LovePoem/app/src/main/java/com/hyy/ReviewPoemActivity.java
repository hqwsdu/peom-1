package com.hyy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ReviewPoemActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvContent;
    private DBHelper dbHelper;
    private List<PoemBean> list;
    private Button btLast;
    private Button btCollect;
    private Button btNext;
    private Button btSpeak;


    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_content);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        btLast = findViewById(R.id.bt_last);
        btCollect = findViewById(R.id.bt_collect);
        btNext = findViewById(R.id.bt_next);
        btSpeak = findViewById(R.id.bt_speak);
        setTitle("复习本");

        btLast.setOnClickListener(this);
        btCollect.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btSpeak.setOnClickListener(this);
        tvTitle.setText("第"+(index + 1)+"首");

        dbHelper = new DBHelper(getApplicationContext());
        list = dbHelper.rawQuery(DBHelper.FILE_IS_COLLECT,"1");
        if(list.size() > 0) {
            setTvContent(0);
            updateButtonStatus();
            btCollect.setText("取消收藏");
        }
        else
        {
            tvTitle.setText("没有收藏");
            btCollect.setEnabled(false);
            btLast.setEnabled(false);
            btNext.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainMenuActivity.stopSpeaking();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_last:
                last();
                break;
            case R.id.bt_collect:
                cancleCollect();
                break;
            case R.id.bt_next:
                next();
                break;
            case R.id.bt_speak:
                PoemBean bean = list.get(index);
                MainMenuActivity.speak(bean.getPoemContent());
                break;
        }
        updateButtonStatus();
    }

    /**
     * 收藏
     */
    private void cancleCollect()
    {
        PoemBean bean = list.get(index);
        bean.setCollect(0);
        dbHelper.update(bean);
        Toast.makeText(getApplicationContext(),"取消收藏成功！",Toast.LENGTH_SHORT).show();
        list.remove(index);
        if(index >= list.size())
        {
            index = list.size() - 1;
        }
        if(list.size() > 0 ) {
            setTvContent(index);
        }
        else
        {
            tvTitle.setText("没有收藏");
            tvContent.setText("");
            btCollect.setEnabled(false);
            btLast.setEnabled(false);
            btNext.setEnabled(false);
            index = -1;
        }
    }

    /**
     * 上一首
     */
    private void last()
    {
        if(index == 0)
        {
            Toast.makeText(getApplicationContext(),"已经是第一首了！",Toast.LENGTH_SHORT).show();
        }
        else
        {
            index -- ;
            setTvContent(index);
        }
    }

    /**
     * 下一首
     */
    private void next()
    {
        if(index == list.size() - 1)
        {
            Toast.makeText(getApplicationContext(),"已经是最后一首了！",Toast.LENGTH_SHORT).show();
        }
        else
        {
            index ++ ;
            setTvContent(index);
        }
    }

    /**
     * 设置显示的内容
     * @param index
     */
    private void setTvContent(int index)
    {
        if(index < list.size())
        {
            PoemBean bean = list.get(index);
            tvContent.setText(bean.getPoemContent());
            tvTitle.setText("第"+(index + 1)+"首(共"+list.size()+"首)");
        }else if(list.size() == 0)
        {
            tvTitle.setText("没有收藏");
            btCollect.setEnabled(false);
            btLast.setEnabled(false);
            btNext.setEnabled(false);
            btSpeak.setEnabled(false);
        }
    }

    /**
     * 更新按钮状态，第一首时，上一首按钮不可用，最后一首时，下一首按钮不可用
     */
    private void updateButtonStatus()
    {
        btLast.setEnabled(false);
        btNext.setEnabled(false);
        if(index > 0)
        {
            btLast.setEnabled(true);
        }
        if(index < list.size() - 1)
        {
            btNext.setEnabled(true);
        }

    }
}
