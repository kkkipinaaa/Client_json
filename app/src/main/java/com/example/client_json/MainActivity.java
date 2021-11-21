package com.example.client_json;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final OkHttpClient mClient = new OkHttpClient();

    private Button mButton;
    private TextView mTextView;
    private EditText mEditText;
    private AsyncTask mAsyncTask;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onStop () {
        super.onStop();
        mAsyncTask.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick (View view) {
        String URL = "http://api.languagelayer.com/detect" +
                "?access_key=f07f3e73f544a34c3bb134783a672b13" +
                "&query=";

        String textFromTV = mEditText.getText().toString();

        try {
            URL = URL + URLEncoder.encode(textFromTV, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mAsyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground (String... strings) {
                String result = null;
                String url = strings[0];
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try (Response response = mClient.newCall(request).execute()) {
                    if (!response.isSuccessful() && response.body() == null) {
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
            protected void onPostExecute (String s) {
                mTextView.setText(s);
            }
        }.execute(URL);

    }

    private void initViews () {
        mButton = findViewById(R.id.button);
        mEditText = findViewById(R.id.editText);
        mTextView = findViewById(R.id.textView);
        mButton.setOnClickListener(this);
    }
}