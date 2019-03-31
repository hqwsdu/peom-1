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
import java.util.Timer;
import java.util.TimerTask;

public class GuessPoemActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup rgDifficulty;
    private RadioButton rbEasy;
    private RadioButton rbNormal;
    private RadioButton rbDifficut;
    private Button btStart;
    private EditText etCorrectAns;
    private Button btSubmit;
    private ImageView ivQuestion;
    private TextView tvAnswer;
    private TextView tvTips;

    private int [] pics= {R.drawable.q1,R.drawable.q2,R.drawable.q3,
            R.drawable.q4,R.drawable.q5,R.drawable.q6,R.drawable.q7,R.drawable.q8,R.drawable.q9,R.drawable.q10};
    private String [] poems ;
    private int picIndex = 0;
    private int difficulty = 10;//困难度，10s每首诗
    private int points = 0;
    private Random random ;
    private int correct_ans_index = 0;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(picIndex < 10)
            {
                showNewQuestion();
                handler.postDelayed(runnable,difficulty*1000);
            }
            else
            {
                handler.removeCallbacks(runnable);
                tvAnswer.setText("游戏结束，分数是："+points);
                btStart.setEnabled(true);
                btSubmit.setEnabled(false);
                picIndex = 0;
            }

        }
    };

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_poem);
        rgDifficulty = findViewById(R.id.rg_difficulty);
        rbEasy = findViewById(R.id.rb_easy);
        rbNormal = findViewById(R.id.rb_normal);
        rbDifficut = findViewById(R.id.rb_difficult);
        btStart = findViewById(R.id.bt_start);
        etCorrectAns = findViewById(R.id.et_correct_ans);
        btSubmit = findViewById(R.id.bt_submit);
        ivQuestion = findViewById(R.id.image_view);
        tvAnswer = findViewById(R.id.tv_answer);
        tvTips = findViewById(R.id.tv_tips);
        btStart.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        poems = getResources().getStringArray(R.array.guess_poem_array);
        handler = new Handler();
        random = new Random();
        tvTips.setText("时间说明：[简单]每道题40秒，[普通]每道题20秒，[困难每道题10秒]");
        btSubmit.setEnabled(false);
    }


    private void showNewQuestion()
    {
        ivQuestion.setImageResource(pics[picIndex]);
        correct_ans_index = random.nextInt(3);
        int []ans = getRandomNums(correct_ans_index);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < ans.length;i++)
        {
            stringBuilder.append("[ ").append(i).append(" ]").append(poems[ans[i]]).append("\n\n");
        }
        tvAnswer.setText(stringBuilder.toString());
        tvTips.setText("提示：共10题，每道题的答题时间是 " + difficulty+" 秒！第"+(picIndex+1)+"题。");
        picIndex ++;
    }

    int [] getRandomNums(int correct_ans_index)
    {
        int [] numbers = new int[4];
        numbers[correct_ans_index] = picIndex;
        boolean again = true;
        int temp = 0;
        for(int k = 0; k < 4; k++)
        {
            if(k != correct_ans_index)
            {
                again = true;
                while(again)//生成一个不相等的随机数
                {
                    temp = random.nextInt(9);
                    again = false;
                    for (int i = 0; i < k; i++) {
                        if (numbers[i] == temp || picIndex == temp) {
                            again = true;
                            break;
                        }
                    }
                }
                numbers[k] = temp;
            }
        }
        return numbers;
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

    private void submit()
    {
        if(String.valueOf(correct_ans_index).equals(etCorrectAns.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"恭喜，答案正确。",Toast.LENGTH_SHORT).show();
            points++;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"遗憾，答案错误。",Toast.LENGTH_SHORT).show();
        }
        if(picIndex < pics.length )
        {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable,0);
        }
        else
        {
            handler.removeCallbacks(runnable);
            tvAnswer.setText("游戏结束，分数是："+points);
            btStart.setEnabled(true);
            btSubmit.setEnabled(false);
            picIndex = 0;
        }
    }

    /**
     * 开始的游戏的操作
     */
    private void startGame()
    {
        if(rbEasy.isChecked())
        {
            difficulty = 40;
        }
        else if(rbNormal.isChecked())
        {
            difficulty = 20;
        }
        else if(rbDifficut.isChecked())
        {
            difficulty = 10;
        }
        showNewQuestion();
        handler.postDelayed(runnable,difficulty*1000);
        btStart.setEnabled(false);
        btSubmit.setEnabled(true);
    }
}
