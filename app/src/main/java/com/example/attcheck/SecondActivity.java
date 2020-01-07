package com.example.attcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondActivity extends AppCompatActivity {

    private String name;
    private TextView tvData;
    private TextView NameText;
    private Button NextButton;



    String SERVER = "http://192.249.19.252:1780/students/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);

        init();

        // MainActivity 로부터 사용자 이름 가져옴.
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        NameText.setText(name);

        // 서버 url 에 사용자 이름 추가
        SERVER = SERVER + name;

        // 버튼 클릭시 웹서버로 부터 JSONArray 가져옴.

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpGetRequest request = new HttpGetRequest();
                request.execute();

            }
        });


    }



    // 웹서버에서 사용자 수강과목 JSONArray 데이터 가져와주는 클래스
    public class HttpGetRequest extends AsyncTask<Void, Void, String> {

        static final String REQUEST_METHOD = "GET";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(Void... params){
            String op;
            String inputLine;

            try {
                // connect to the server
                URL myUrl = new URL(SERVER);
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.connect();

                // get the string from the input stream
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                op = stringBuilder.toString();

            } catch(IOException e) {
                e.printStackTrace();
                op = "error";
            }

            return op;
        }

        // 가져온 데이터 tvData 텍스트 뷰에 뿌림
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            tvData.setText(result);
            if (result!=null) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("json", tvData.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }
    }

    public void init(){

        getSupportActionBar().setIcon(R.drawable.tt2);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvData = (TextView) findViewById(R.id.tvdata);
        NameText = (TextView) findViewById(R.id.nameText);
        NextButton = (Button) findViewById(R.id.button3);

    }



}
