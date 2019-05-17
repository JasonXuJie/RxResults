package com.jason.rxresults;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;

/**
 * 避免调用 startActivity 时，需要 onActivityResult 处理的类
 * **/
public class RxResults {

    private static final String TAG = "RxResults";
    private RxResultsFragment mSimpleOnResultFragment;

    public RxResults(AppCompatActivity activity){
        mSimpleOnResultFragment = getResultsFragment(activity.getSupportFragmentManager());
    }

    public RxResults(Fragment fragment){
        mSimpleOnResultFragment = getResultsFragment(fragment.getChildFragmentManager());
    }


    private RxResultsFragment getResultsFragment(FragmentManager manager){
        RxResultsFragment rxResultsFragment = findResultsFragment(manager);
        if (rxResultsFragment == null){
            rxResultsFragment = new RxResultsFragment();
            manager.beginTransaction()
                    .add(rxResultsFragment,TAG)
                    .commitAllowingStateLoss();
            manager.executePendingTransactions();
        }
        return rxResultsFragment;
    }

    private RxResultsFragment findResultsFragment(FragmentManager manager){
        return (RxResultsFragment) manager.findFragmentByTag(TAG);
    }

    public Observable<ResultInfo> startForResult(Intent intent){
        return mSimpleOnResultFragment.startForResult(intent);
    }

    public Observable<ResultInfo> startForResult(Class<?> cls){
        Intent intent = new Intent(mSimpleOnResultFragment.getActivity(),cls);
        return startForResult(intent);
    }

    public void startForResult(Intent intent,CallBack callBack){
        mSimpleOnResultFragment.startForResult(intent,callBack);
    }

    public void startForResult(Class<?> cls, RxResults.CallBack callBack){
        Intent intent = new Intent(mSimpleOnResultFragment.getActivity(),cls);
        startForResult(intent,callBack);
    }


    public interface CallBack{
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
