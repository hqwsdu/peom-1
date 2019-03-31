package com.hyy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PoemGameMenuActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_game);
        Button btGuessPoem = findViewById(R.id.bt_guess_poem);
        Button btCompletePoem = findViewById(R.id.bt_complete_poem);

        btGuessPoem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PoemGameMenuActivity.this,GuessPoemActivity.class);
                startActivity(intent);
            }
        });

        btCompletePoem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PoemGameMenuActivity.this,CompletePoemGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
