package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import base.BaseActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tools.msgs_class_to_class;
import tools.send_tip_msgs;

public class Register extends BaseActivity {
    private EditText id;
    private EditText password;
    private EditText ipassword;
    private TextView login;
    private Button teacher_permission;
    private Button student_permission;
    private Button register;
    private int permission = 3;
    private final Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        intView();
        intEvent();
    }
    private void intView(){
        id = findViewById(R.id.et_Register_Id);
        password = findViewById(R.id.et_Register_Psw);
        //ipassword = findViewById(R.id.);
        register = findViewById(R.id.register);
        teacher_permission = findViewById(R.id.teacher);
        student_permission= findViewById(R.id.student);
        login = findViewById(R.id.back_To_Login);
        teacher_permission.setEnabled(true);
        student_permission.setEnabled(true);
        Intent intent = getIntent();
        msgs_class_to_class.get_msgs(intent,"id",id);
    }
    private void intEvent(){
        teacher_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission = 1;
                teacher_permission.setEnabled(false);
                student_permission.setEnabled(true);
            }
        });
        student_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission = 0;
                teacher_permission.setEnabled(true);
                student_permission.setEnabled(false);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_id = id.getText().toString();
                String str_password = password.getText().toString();
                if(str_id.equals("")){
                    Toast.makeText(getApplication(), "??????????????????", Toast.LENGTH_SHORT).show();
                }
                else if (str_password.equals("")) {
                    Toast.makeText(getApplicationContext(), "??????????????????", Toast.LENGTH_SHORT).show();
                } else if(permission == 3){
                    Toast.makeText(getApplication(), "?????????????????????", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show();
                    register(str_id,permission,str_password);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
                Intent intent = msgs_class_to_class.sned_msgs(Register.this,Login.class,"id",id.getText().toString());
                startActivity(intent);
            }
        });
    }
    private void register(String id,int permission,String password){
        new Thread(() -> {

            // url??????
            String url = "http://47.107.52.7:88/member/sign/user/register";

            // ?????????
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", "cfa7b08b01fd4c0b90f40f94798cfc2d")
                    .add("appSecret", "17942d0b56a59da3e4d2abbb11b71e98f8b12")
                    .add("Content-Type", "application/json")
                    .build();

            // ?????????
            // PS.??????????????????????????????????????????????????????????????????fastjson???????????????json???
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("password",id);
            bodyMap.put("roleId", permission);
            bodyMap.put("userName", password);
            // ???Map??????????????????????????????????????????
            String body = gson.toJson(bodyMap);

            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

            //??????????????????
            Request request = new Request.Builder()
                    .url(url)
                    // ???????????????????????????
                    .headers(headers)
                    .post(RequestBody.create(MEDIA_TYPE_JSON, body))
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //?????????????????????callback????????????
                client.newCall(request).enqueue(callback);
            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * ??????
     */
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            //TODO ??????????????????
            e.printStackTrace();
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO ??????????????????
            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // ??????????????????json???
            String body = response.body().string();
            Log.d("info", body);
            // ??????json???????????????????????????
            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
            Log.d("info", dataResponseBody.toString());
            send_tip_msgs.showToast(Register.this,dataResponseBody.getMsg());
            if(dataResponseBody.getCode()==200){
                Intent intent = msgs_class_to_class.sned_msgs(Register.this,Login.class,"id",id.getText().toString());
                startActivity(new Intent(Register.this,Login.class));
                startActivity(intent);
            }
        }
    };

    /**
     * http????????????????????????
     * @param <T> ??????
     */
    public static class ResponseBody <T> {

        /**
         * ???????????????
         */
        private int code;
        /**
         * ??????????????????
         */
        private String msg;
        /**
         * ????????????
         */
        private T data;

        public ResponseBody(){}

        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
        public T getData() {
            return data;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseBody{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
