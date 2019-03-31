package com.hyy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;

public class AutoCreatePoemActivity extends AppCompatActivity {

    private RadioGroup rgStyle;
    private RadioButton rbQingxin;
    private RadioButton rbDaqi;
    private RadioButton rbBeishang;
    private EditText etKeyword1;
    private EditText etKeyword2;
    private TextView tvContent;
    private TextView tvTips1;
    private TextView tvTips2;
    private Button btSubmit;
    private int style = 0;

    private String [] poemQingxin ;
    private String [] poemDaqi;
    private String [] poemBeishang;
    private String [] tips1 = {" 一种小动物，如游鱼、燕子等，两字。","跟山有关的词，如山顶、山坡等，两字。"," 一种古乐器，如古筝、琵琶等，两字。"};
    private String [] tips2 = {" 一种花香，如荷香、兰香等，两字。","跟海有关的词，如海波、大海等，两字。"," 一种悲伤的心情，如心痛、伤心等，两字。"};
    private Random random;
    private int positionQingxin = 0;
    private int positionDaqi = 0;
    private int positionBeishang = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_create_poem);
        rgStyle = findViewById(R.id.rg_style);
        rbQingxin  = findViewById(R.id.rb_qingxin);
        rbDaqi = findViewById(R.id.rb_daqi);
        rbBeishang = findViewById(R.id.rb_beishang);
        etKeyword1 = findViewById(R.id.et_keyword1);
        etKeyword2 = findViewById(R.id.et_keyword2);
        tvContent = findViewById(R.id.tv_content);
        btSubmit = findViewById(R.id.bt_submit);
        tvTips1 = findViewById(R.id.tv_tips1);
        tvTips2 = findViewById(R.id.tv_tips2);

        poemQingxin = getResources().getStringArray(R.array.poem_qingxin);
        poemDaqi = getResources().getStringArray(R.array.poem_daqi);
        poemBeishang = getResources().getStringArray(R.array.poem_beishang);
        tvTips1.setText("提示：关键词1，"+tips1[style]);
        tvTips2.setText("提示：关键词2，"+tips2[style]);
        random = new Random();

        rgStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rbQingxin.isChecked()) {
                    style = 0;
                }else  if(rbDaqi.isChecked()){
                    style = 1;
                }else if(rbBeishang.isChecked()){
                    style = 2;
                }
                tvTips1.setText("提示：关键词1，"+tips1[style]);
                tvTips2.setText("提示：关键词2，"+tips2[style]);
            }
        });


        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content;
                if(style == 0)
                {

                    content = poemQingxin[positionQingxin];
                    content = content.replace("1",etKeyword1.getText().toString());
                    content = content.replace("2",etKeyword2.getText().toString());
                    tvContent.setText(content);
                    positionQingxin ++;
                    if(positionQingxin >= poemQingxin.length)
                    {
                        positionQingxin = 0;
                    }

                }else if(style == 1)
                {

                    content = poemDaqi[positionDaqi];
                    content = content.replace("1",etKeyword1.getText().toString());
                    content = content.replace("2",etKeyword2.getText().toString());
                    tvContent.setText(content);
                    positionDaqi ++;
                    if(positionDaqi >= poemDaqi.length)
                    {
                        positionDaqi = 0;
                    }
                }else if(style == 2)
                {

                    content = poemBeishang[positionBeishang];
                    content = content.replace("1",etKeyword1.getText().toString());
                    content = content.replace("2",etKeyword2.getText().toString());
                    tvContent.setText(content);
                    positionBeishang ++;
                    if(positionBeishang >= poemBeishang.length)
                    {
                        positionBeishang = 0;
                    }
                }

            }
        });


    }

}
