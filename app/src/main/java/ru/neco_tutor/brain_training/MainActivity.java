package ru.neco_tutor.brain_training;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.neco_tutor.brain_training.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String TAG = getClass().getSimpleName();
    private final String KEY = "save_key";

    private TextView textView_Score;
    private TextView textView_task;
    private Button btn_true;
    private Button btn_false;
    private ActionBar actionBar;
    private int num1;
    private int num2;
    private int num_index;
    private int num_false;
    private int num_res;
    private int max = 30;
    private int min = 1;
    private int max_false = 3;
    private int min_false = -3;
    private int true_answer = 0;
    private int max_answer = 10;
    private boolean is_true_answer = false;
    private String task;
    private long start_time = 0;
    private long current_time = 0;
    private float time_result = 0.0f;
    private float best_time_result = 0.0f;


    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_true_answer) {
                    true_answer++;
                    textView_Score.setText("Your Score: " + true_answer);
                }
                current_time = System.currentTimeMillis();
                time_result = (float) (current_time - start_time) / 1000;
                String time = "Time: " + time_result;
                actionBar.setTitle(time);
                genNumbers();
            }
        });

        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!is_true_answer) {
                    true_answer++;
                    textView_Score.setText("Your Score: " + true_answer);
                }
                current_time = System.currentTimeMillis();
                time_result = (float) (current_time - start_time) / 1000;
                String time = "Time: " + time_result;
                actionBar.setTitle(time);
                genNumbers();
            }
        });

    }

    private void init() {
        start_time = System.currentTimeMillis();

        textView_Score = binding.textViewScore;
        textView_task = binding.textViewTask;
        btn_true = binding.btnTrue;
        btn_false = binding.btnFalse;

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        preferences = getSharedPreferences("Best Score", MODE_PRIVATE);
        genNumbers();

    }

    private void genNumbers() {
        num1 = (int) (Math.random() * (max - min));
        num2 = (int) (Math.random() * (max - min));
        num_false = num1 + num2 + (int) (Math.random() * (max_false - min_false));
        num_res = num1 + num2;
        num_index = (int) (Math.random() * (5 - 0));

        if (num_index > 1) {
            task = num1 + " + " + num2 + " = " + num_res;
            is_true_answer = true;
        } else {
            task = num1 + " + " + num2 + " = " + num_false;
            is_true_answer = false;
        }
        Log.d(TAG, num1 + " " + num2 + " " + num_false + " " + num_res + " " + is_true_answer);

        textView_task.setText(task);
        if(true_answer >= max_answer){
            best_time_result = preferences.getFloat(KEY, 0.0f);
            if(time_result < best_time_result){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat(KEY, time_result);
                editor.apply();
            }
            Intent intent = new Intent(MainActivity.this, FinishActivity.class);
            intent.putExtra("res_time", time_result);
            intent.putExtra("best_time", best_time_result);
            startActivity(intent);

        }
    }
}