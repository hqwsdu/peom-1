package com.hyy;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CompletePoemGameActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup rgDifficulty;
    private RadioButton rbEasy;
    private RadioButton rbNormal;
    private RadioButton rbDifficut;
    private Button btStart;
    private EditText etCorrectAns;
    private Button btSubmit;
    private TextView tvQuestion;
    private TextView tvTips;

    private int questionIndex = 0;
    private int maxQuestionNum = 0;
    private int difficulty = 50;
    private int points;
    private String missSentence;
    private String []poems ;
    private Random random ;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(questionIndex < maxQuestionNum )
            {
                showNewQuestion();
                handler.postDelayed(runnable,difficulty*1000);
            }
            else
            {
                handler.removeCallbacks(runnable);
                tvQuestion.setText("游戏结束，分数是："+points);
                btStart.setEnabled(true);
                btSubmit.setEnabled(false);
                questionIndex = 0;
            }

        }
    };

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_poem_game);
        rgDifficulty = findViewById(R.id.rg_difficulty);
        rbEasy = findViewById(R.id.rb_easy);
        rbNormal = findViewById(R.id.rb_normal);
        rbDifficut = findViewById(R.id.rb_difficult);
        btStart = findViewById(R.id.bt_start);
        etCorrectAns = findViewById(R.id.et_correct_ans);
        btSubmit = findViewById(R.id.bt_submit);
        tvQuestion = findViewById(R.id.tv_answer);
        tvTips = findViewById(R.id.tv_tips);
        btStart.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        poems = getResources().getStringArray(R.array.complete_poem_array);
        maxQuestionNum = poems.length;
        random = new Random();
        handler = new Handler();
        btSubmit.setEnabled(false);
        tvTips.setText("时间说明：[简单]每道题80秒，[普通]每道题40秒，[困难每道题20秒]");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_start:
                startGame();
                break;
            case R.id.bt_submit:
                submit();
                break;

        }
    }

    /*提交答案*/
    private void submit()
    {
         if((!missSentence.isEmpty()) && missSentence.equals(etCorrectAns.getText().toString()))
         {
             Toast.makeText(getApplicationContext(),"恭喜，答案正确。",Toast.LENGTH_SHORT).show();
             points++;
         }
         else
         {
             Toast.makeText(getApplicationContext(),"遗憾，答案错误。",Toast.LENGTH_SHORT).show();
         }
         if(questionIndex < poems.length)
         {
             handler.removeCallbacks(runnable);
             handler.postDelayed(runnable,0);
         }
         else
         {
            handler.removeCallbacks(runnable);
            tvQuestion.setText("游戏结束，分数是："+points);
            btStart.setEnabled(true);
            btSubmit.setEnabled(false);
            questionIndex = 0;
        }
    }

    private void showNewQuestion()
    {
        String temp = poems[questionIndex++];
        String []poemContent = temp.split(",");
        int randNum = random.nextInt(poemContent.length -1);
        missSentence = poemContent[randNum];//保存需要填入的诗词
        poemContent[randNum] = "?????";//待填空的题用问号代替

        StringBuilder sb = new StringBuilder();
        for(String str:poemContent)
        {
            sb.append(str).append(" \n");
        }
        tvQuestion.setText(sb.toString());
        tvTips.setText("提示：总共"+maxQuestionNum+"道题，每道题"+difficulty+"秒钟答题时间。第"+questionIndex+"题。");
    }
    /**
     * 开始的游戏的操作
     */
    private void startGame()
    {
        btSubmit.setEnabled(true);
        if(rbEasy.isChecked())
        {
            difficulty = 80;
        }
        else if(rbNormal.isChecked())
        {
            difficulty = 40;
        }
        else if(rbDifficut.isChecked())
        {
            difficulty = 20;
        }
        showNewQuestion();
        handler.postDelayed(runnable,difficulty*1000);
        btStart.setEnabled(false);
    }
}
