package com.luckyaf.goodatmaster;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import goodat.weaving.HookMethod;
import goodat.weaving.NeedPermission;
import goodat.weaving.ThrottleFirst;
import goodat.weaving.Tracking;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onStart() {
        super.onStart();
    }


    private void initView(){
        tvContent = findViewById(R.id.tvContent);
        findViewById(R.id.btnThrottleFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSomeThing();
            }
        });
    }

    @ThrottleFirst(3000)
    public void doSomeThing(){
        num += 1;
        tvContent.setText("执行"+ num + "次");
        showSomeThing(num);
    }


    @Tracking
    private void showSomeThing(int num){
        Toast.makeText(this,"数字"+num,Toast.LENGTH_SHORT).show();
    }


//
//    @HookMethod(beforeMethod = "doSomeThing",afterMethod ="takePhoto")
//    public void hookMethod(){
//
//    }

}
