package me.yimu.demo.concurrent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.action_foo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntentService.startActionFoo(MainActivity.this, "111", "222");
            }
        });

        findViewById(R.id.action_baz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntentService.startActionBaz(MainActivity.this, "111", "222");
            }
        });
    }
}
