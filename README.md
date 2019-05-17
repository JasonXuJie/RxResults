# RxResults
基于RxPermissions框架思想，在Activity或Fragment使用startActivityForResult可以不重写onActivityResult回调


在Activity中使用
```
findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxResults rxResults = new RxResults(MainActivity.this);
                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                intent.putExtra("flag","flag");
                //可以传递数据
                //rxResults.startForResult(TestActivity.class)//可以不传递数据
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
                                //也可以根据getResultCode根据不同的code进行判断
                                //switch (activityResultInfo.getResultCode()){
                                //    case RESULT_OK:
                                //        Logger.e("1:"+activityResultInfo.getData().getStringExtra("result"));
                                //        break;
                                //    case RESULT_CANCELED:
                                //        Logger.e("2:"+activityResultInfo.getData().getStringExtra("result"));
                                //        break;
                                //}
                            }
                        });
            }
        });
```     
   
   
在Fragment中使用
```
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
```        
