package id.egin.threadtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    private Button btnStart, btnStop;
    private Thread bgthread;
    private int value;
    private Random myRandom;
    private boolean condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRandom = new Random();
        condition = true;

        text = findViewById(R.id.textView1);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condition = true;

                if(bgthread == null || bgthread.getState() == Thread.State.TERMINATED) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (condition) {
                                    value = myRandom.nextInt(10);
                                    Thread.sleep(500);

                                    text.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            text.setText(String.valueOf(value));
                                        }
                                    });
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    bgthread = new Thread(runnable);
                    bgthread.start();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condition = false;
                bgthread.interrupt();
            }
        });
    }
}