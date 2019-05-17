package com.jason.rxresults;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxResults rxResults = new RxResults(MainActivity.this);
                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                intent.putExtra("flag","flag");
                //TestActivity.class
                rxResults.startForResult(intent)
                        .subscribe(new Consumer<ResultInfo>() {
                            @Override
                            public void accept(ResultInfo resultInfo) throws Exception {
                                if (resultInfo.getData()!=null){
                                    Log.e("11","有数据");
                                    String data = resultInfo.getData().getStringExtra("data");
                                    Log.e("data",data);
                                }else {
                                    Log.e("11","无数据");
                                }
                            }
                        });
            }
        });
    }
}
