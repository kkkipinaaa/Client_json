package com.example.client_json;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback {

    private final OkHttpClient client = new OkHttpClient();

    private Button button;
    private TextView textView;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        button.setOnClickListener(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        final String URL = "http://api.languagelayer.com/detect" + "? access_key = f07f3e73f544a34c3bb134783a672b13" + "& query = ";

        String textFromTV = editText.getText().toString();
        String  U = null;

        try {
            U = URL + URLEncoder.encode(textFromTV, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url(U)
                .tag("request")
                .build();

        client.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        textView.setText("Что-то пошло не так!");
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        if (!response.isSuccessful()) {
            String message = "Что-то пошло не так!\nОшибка: " + response.code();
            textView.setText(message);
        }

        try (ResponseBody responseBody = response.body()) {

        }
    }

}