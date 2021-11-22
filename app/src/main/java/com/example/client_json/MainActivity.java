package com.example.client_json;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final OkHttpClient mClient = new OkHttpClient();

    private Button mButton;
    private TextView mTextView;
    private static AsyncTask mAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAsyncTask.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View view) {

        String URL = "http://10.0.2.2:8095/Test";


        mAsyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String result = null;
                String url = strings[0];

                RequestBody formbody = new FormBody.Builder()
                        .add("message", "Это работает")
                        .add("date", "14.11.2021")
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(formbody)
                        .build();

                try (Response response = mClient.newCall(request).execute()) {
                    if ((!response.isSuccessful()) && (response.body() == null)) {
                        result = "Что-то пошло не так!";

                    } else {
                        result = response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;

            }

            @Override
            protected void onPostExecute(String s) {
                mTextView.setText(s);
            }
        }.execute(URL);
    }

    private void initViews() {
        mButton = findViewById(R.id.button);
        mTextView = findViewById(R.id.textView);
        mButton.setOnClickListener(this);
    }
}