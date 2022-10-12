package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import base.BaseActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MycourseActivity extends BaseActivity {

    public RecyclerView recyclerView;
    private CourseListAdapter courseListAdapter;
    private TextView view;
    private static final String TAG = "MenuActivity";

    private ArrayList<RecordsBean> list;

    static final int SUCCESS = 0;
    static final int FAILURE = 1;

    Handler handler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            switch (msg.what)
            {
                case SUCCESS:
                    courseListAdapter.notifyDataSetChanged();
                    break;
                case FAILURE:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse);
        view= findViewById(R.id.image);
        list=new ArrayList<>();

        MenuRequest();

        recyclerView=findViewById(R.id.menu_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(MycourseActivity.this));

        courseListAdapter=new CourseListAdapter(MycourseActivity.this,R.layout.activity_course,list);

        recyclerView.setAdapter(courseListAdapter);
    }
    public void MenuRequest(){
        Headers headers=new Headers.Builder()
                .add("Accept","application/json, text/plain, */*")
                .add("Content-Type","application/json")
                .add("appId", "1c53542a2bfa43b2a690ad8b8e987301")
                .add("appSecret", "03998a873144910df439eb0691b841601a601")
                .build();

        int userId=532;
        //组合请求
        Request request=new Request.Builder()
                .url("http://47.107.52.7:88/member/sign/course/student?userId="+userId)
                .headers(headers)
                .get()
                .build();
        //发送请求
        OkHttpClient client = new OkHttpClient();
        //发起请求，传入callback进行回调
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                Type userListType = new TypeToken<courseList>(){}.getType();
                courseList courseLists=gson.fromJson(response.body().string(),userListType);
                list.addAll(courseLists.getData().getRecords());
                Log.d(TAG, "onResponse: "+list.get(0).getCollegeName());
                handler.sendEmptyMessage(SUCCESS);
            }
        });
    }
    

    // 请注意本方法内容的变化
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.btnNavHome:
                open(HomeActivity.class);
                break; // case R.id.btnNavHome

            case R.id.btnNavSettings:
                open(PersonActivity.class);
                break; // case R.id.btnNavSettings
        } // switch (v.getId())
    } // onNavButtonsTapped()

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        } // if (keyCode == KeyEvent.KEYCODE_BACK)
        else {
            return super.onKeyDown(keyCode, event);
        }
    }
}



