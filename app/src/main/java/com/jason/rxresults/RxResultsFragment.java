package com.jason.rxresults;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxResultsFragment extends Fragment {

    private static Map<Integer, PublishSubject<ResultInfo>> mSubjects = new HashMap<>();
    private static Map<Integer, RxResults.CallBack> mCallbacks = new HashMap<>();


    public RxResultsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public Observable<ResultInfo> startForResult(final Intent intent) {
        int requestCode = generateRequestCode();
        PublishSubject<ResultInfo> subject = PublishSubject.create();
        mSubjects.put(requestCode, subject);
        startActivityForResult(intent, requestCode);
        return subject;
    }

    public void startForResult(Intent intent, RxResults.CallBack callback) {
        int requestCode = generateRequestCode();
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //rxjava处理方式
        PublishSubject<ResultInfo> subject = mSubjects.remove(requestCode);
        if (subject != null) {
            subject.onNext(new ResultInfo(requestCode, resultCode, data));
            subject.onComplete();
        }
        //callback方式处理
        RxResults.CallBack callBack = mCallbacks.remove(requestCode);
        if (callBack != null) {
            callBack.onActivityResult(requestCode, resultCode, data);
        }
    }

    private int generateRequestCode() {
        Random random = new Random();
        for (; ; ) {
            int code = random.nextInt(65536);
            if (!mSubjects.containsKey(code) && !mCallbacks.containsKey(code)) {
                return code;
            }
        }
    }


}
