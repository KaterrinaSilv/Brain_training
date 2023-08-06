package ru.neco_tutor.brain_training;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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

    private int answer_result = 0;
    private int best_answer_result = 0;
    private boolean is_true_answer = false;
    private String task;
    private int symbol;


    // таймер

    private CountDownTimer countDownTimer;
    private static final long START_TIME = 60000;
    private boolean is_timer_running = false;
    private long time_left = START_TIME;


    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        if (!is_timer_running) {
            startTimer();
        }
        updateTimerView();

        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_true_answer) {
                    true_answer++;
                    textView_Score.setText("Ваш счет: " + true_answer);
                }
                genNumbers();
            }
        });

        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_true_answer) {
                    true_answer++;
                    textView_Score.setText("Ваш счет: " + true_answer);
                }
                genNumbers();
            }
        });

    }

    private void updateTimerView() {
        int minuets = (int) (time_left / 1000) / 60;
        int seconds = (int) (time_left / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minuets, seconds);
        actionBar.setTitle("Оставшееся время: " + timeLeftFormatted);

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(time_left, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left = millisUntilFinished;
                updateTimerView();
            }

            @Override
            public void onFinish() {
                answer_result = true_answer;
                best_answer_result = preferences.getInt(KEY, 0);
                if (answer_result > best_answer_result) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(KEY, answer_result);
                    editor.apply();
                }
                Intent intent = new Intent(MainActivity.this, FinishActivity.class);
                intent.putExtra("res_answer", answer_result);
                intent.putExtra("best_res_answer", best_answer_result);
                startActivity(intent);

            }
        }.start();
        is_timer_running = true;
    }

    private void init() {
        textView_Score = binding.textViewScore;
        textView_task = binding.textViewTask;
        btn_true = binding.btnTrue;
        btn_false = binding.btnFalse;

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        preferences = getSharedPreferences("Best_Answers_Score", MODE_PRIVATE);
        genNumbers();

    }

    private void genNumbers() {
        num1 = (int) (Math.random() * (max - min));
        num2 = (int) (Math.random() * (max - min));
        symbol = (int) (Math.random() * (5 - 0));
        if (symbol > 1) {
            //+
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
        } else {
            //-
            num_false = num1 - num2 + (int) (Math.random() * (max_false - min_false));
            num_res = num1 - num2;
            num_index = (int) (Math.random() * (5 - 0));

            if (num_index > 1) {
                task = num1 + " - " + num2 + " = " + num_res;
                is_true_answer = true;
            } else {
                task = num1 + " - " + num2 + " = " + num_false;
                is_true_answer = false;
            }
        }

        Log.d(TAG, num1 + " " + num2 + " " + num_false + " " + num_res + " " + is_true_answer);

        textView_task.setText(task);

    }
}
