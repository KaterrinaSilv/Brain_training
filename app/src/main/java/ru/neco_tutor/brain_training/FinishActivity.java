package ru.neco_tutor.brain_training;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.neco_tutor.brain_training.databinding.ActivityFinishBinding;

public class FinishActivity extends AppCompatActivity {
    private ActivityFinishBinding binding;
    private TextView tv_result;
    private TextView tv_best_result;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFinishBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init(){
        tv_result =binding.tvResult;
        tv_best_result = binding.tvBestResult;
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        setResult();
    }
    private void setResult(){
        Bundle results = getIntent().getExtras();
        if (results == null){
            tv_result.setText("0");
            tv_best_result.setText("0");
        }else {
            tv_result.setText("Ваш результат: " + results.getInt("res_answer"));
            tv_best_result.setText("Лучшее время: " + results.getInt("best_res_answer"));
        }

    }
}