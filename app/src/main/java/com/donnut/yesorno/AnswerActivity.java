package com.donnut.yesorno;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

public class AnswerActivity extends AppCompatActivity {

    private static final String EXTRA_QUESTION = "question";

    private TextView textViewQuestion;
    private TextView textViewAnswer;
    private ImageView imageViewAnswer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        initViews();
        setUpQuestion();
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadYesNoImage();
        viewModel.getIsLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
        viewModel.getIsError().observe(this, isError -> {
            if (isError) {
                Toast.makeText(AnswerActivity.this, R.string.error_loading, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getYesNoImage().observe(this, yesNoImage -> {
            Glide.with(AnswerActivity.this)
                    .load(yesNoImage.getImage())
                    .into(imageViewAnswer);
            String answer = yesNoImage.getAnswer();
            if (answer.equals("yes")) {
                textViewAnswer.setText(getString(R.string.answer_yes).toUpperCase());
                textViewAnswer.setBackgroundColor(getColor(R.color.yes));
            } else if (answer.equals("no")) {
                textViewAnswer.setText(getString(R.string.answer_no).toUpperCase());
                textViewAnswer.setBackgroundColor(getColor(R.color.no));
            }
        });
    }

    public static Intent newIntent(Context context, String question) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(EXTRA_QUESTION, question);
        return intent;
    }

    private void setUpQuestion() {
        String question = getIntent().getStringExtra(EXTRA_QUESTION);
        textViewQuestion.setText(question);
    }

    private void initViews() {
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewAnswer = findViewById(R.id.textViewAnswer);
        imageViewAnswer = findViewById(R.id.imageViewAnswer);
        progressBar = findViewById(R.id.progressBar);
    }
}