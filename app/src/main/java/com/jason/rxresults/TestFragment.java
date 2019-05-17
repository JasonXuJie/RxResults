package com.jason.rxresults;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.functions.Consumer;

public class TestFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test,container,false);
        view.findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxResults rxResults = new RxResults(TestFragment.this);
                Intent intent = new Intent(getActivity(),TestActivity.class);
                intent.putExtra("flag","flag");
                rxResults.startForResult(intent)
                        .subscribe(new Consumer<ResultInfo>() {
                            @Override
                            public void accept(ResultInfo resultInfo) throws Exception {
                                if (resultInfo.getData()!=null){
                                    Log.e("有数据",resultInfo.getData().getStringExtra("data"));
                                }else {
                                    Log.e("无数据","无数据");
                                }
                            }
                        });
            }
        });
        return view;
    }
}
