package com.donnut.yesorno;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextQuestion;
    private Button buttonAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        buttonAnswer.setOnClickListener(view -> {
            String question = editTextQuestion.getText().toString().trim();
            if (question.isEmpty()) {
                Toast.makeText(MainActivity.this, R.string.empty_field, Toast.LENGTH_SHORT).show();
            } else {
                launchNextScreen(question);
            }
        });
    }

    private void launchNextScreen(String question) {
        Intent intent = AnswerActivity.newIntent(this, question);
        startActivity(intent);
    }

    private void initViews() {
        editTextQuestion = findViewById(R.id.editTextQuestion);
        buttonAnswer = findViewById(R.id.buttonAnswer);
    }
}