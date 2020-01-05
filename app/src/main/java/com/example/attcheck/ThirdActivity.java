package com.example.attcheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    private TextView tv;
    private String data;
    private ArrayList<Lecture> LectureList= new ArrayList<>();
    RecyclerView recyclerView;
    private String name;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        init();

        Intent intent = getIntent();
        data = intent.getExtras().getString("json");

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);

        jsonParsing();

        if (data != null) {
            tv.setText(id+"  "+name);
        }

    }

    public void init(){
        tv = (TextView) findViewById(R.id.studentID);
//        recyclerView = findViewById(R.id.recycler);
    }

    // json 파싱
    private void jsonParsing() {
        try {
            // json 을 JSONArray 로 형변환
            JSONArray jsonArray = new JSONArray(data);
            name = jsonArray.getJSONObject(0).getString("name");
            id = jsonArray.getJSONObject(0).getString("student_id");

            for (int i = 0; i<jsonArray.length(); i++) {

                JSONObject lectureObject = jsonArray.getJSONObject(i);

                Lecture lecture = new Lecture();
                lecture.setCode(lectureObject.getString("lecture"));
                lecture.setClassroom(lectureObject.getString("classroom"));

                LectureList.add(lecture);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("틀림!");
        }
    }

    // Lecture 클래스.
    public class Lecture {
        private String code;
        private String classroom;

        public String getCode() {
            return code;
        }

        public String getClassroom() {
            return classroom;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setClassroom(String classroom) {
            this.classroom = classroom;
        }

    }
    /*

    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Lecture> myDataList = new ArrayList<>();

        MyAdapter(ArrayList<Lecture> dataList)
        {
            myDataList = dataList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //전개자(Inflater)를 통해 얻은 참조 객체를 통해 뷰홀더 객체 생성
            View view = inflater.inflate(R.layout.activity_third, parent, false);
            RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
        {
            //ViewHolder가 관리하는 View에 position에 해당하는 데이터 바인딩
            viewHolder.title.setText(myDataList.get(position).getMovieTitle());
            viewHolder.grade.setText(myDataList.get(position).getMovieGrade());

        }

        @Override
        public int getItemCount()
        {
            //Adapter가 관리하는 전체 데이터 개수 반환
            return myDataList.size();
        }
    }

     */
}