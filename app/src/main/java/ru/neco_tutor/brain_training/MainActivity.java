package ru.neco_tutor.brain_training;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private String task;
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
                genNumbers();

            }
        });

        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void init() {
        textView_Score = binding.textViewScore;
        textView_task = binding.textViewTask;
        btn_true = binding.btnTrue;
        btn_false = binding.btnFalse;

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        preferences = getSharedPreferences("Test", MODE_PRIVATE);

    }

    private void genNumbers() {
        num1 = (int) (Math.random() * (max - min));
        num2 = (int) (Math.random() * (max - min));
        num_false = num1 + num2 + (int) (Math.random() * (max_false - min_false));
        num_res = num1 + num2;
        num_index = (int) (Math.random() * (5 - 0));

        Log.d(TAG, num1 + " " + num2 + " " + num_false + " " + num_res + " "+ num_index);

        if(num_index > 1){
            task = num1 + " + " + num2 + " = " + num_res;
        } else {
            task = num1 + " + " + num2 + " = " + num_false;
        }
        textView_task.setText(task);
    }
}