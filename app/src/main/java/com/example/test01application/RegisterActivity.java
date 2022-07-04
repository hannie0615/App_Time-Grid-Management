package com.example.test01application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    // Realtime Database에 접근
    DatabaseReference database = FirebaseDatabase.getInstance().getReference(); // 데베 서버
    DatabaseReference gridnote = database.child("gridnote"); // "gridnote"라는 테이블

    TextView tv_category;
    EditText start_hour, start_min, end_hour, end_min;
    TextView total_min;
    EditText et_memo;
    Button btn_time;
    Button add_button;
    int i=1;
    RadioGroup radio_group;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // view 객체 연결
        radio_group = findViewById(R.id.radio_group);
        tv_category = findViewById(R.id.tv_category);
        start_hour = findViewById(R.id.start_hour);
        start_min = findViewById(R.id.start_min);
        end_hour = findViewById(R.id.end_hour);
        end_min = findViewById(R.id.end_min);
        total_min = findViewById(R.id.total_min);
        et_memo = findViewById(R.id.et_memo);
        add_button = findViewById(R.id.add_button);
        btn_time = findViewById(R.id.btn_time);

        // 라디오 그룹
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_do1:
                        tv_category.setText(INTENT_Category.DO1);
                        break;
                    case R.id.radio_do2:
                        tv_category.setText(INTENT_Category.DO2);
                        break;
                    case R.id.radio_do3:
                        tv_category.setText(INTENT_Category.DO3);
                        break;
                    case R.id.radio_do4:
                        tv_category.setText(INTENT_Category.DO4);
                        break;
                    case R.id.radio_do5:
                        tv_category.setText(INTENT_Category.DO5);
                        break;
                    default:
                        break;
                }
            }
        });

        // total_min 설정해주기
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int getStarthour = Integer.parseInt(start_hour.getText().toString());
                int getStartmin = Integer.parseInt(start_min.getText().toString());
                int getEndhour = Integer.parseInt(end_hour.getText().toString());
                int getEndmin = Integer.parseInt(end_min.getText().toString());

                // 값 전달
                int getTotal = TimeCalculator(getStarthour, getStartmin, getEndhour, getEndmin);
                total_min.setText(Integer.toString(getTotal)+" 분");

            }
        });

        // 값 가져오기
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getCategory = tv_category.getText().toString();
                int getStarthour = Integer.parseInt(start_hour.getText().toString());
                int getStartmin = Integer.parseInt(start_min.getText().toString());
                int getEndhour = Integer.parseInt(end_hour.getText().toString());
                int getEndmin = Integer.parseInt(end_min.getText().toString());
                int getTotal = TimeCalculator(getStarthour, getStartmin, getEndhour, getEndmin);
                String getMemo = et_memo.getText().toString();

                writeNote(Integer.toString(i++), getCategory, getStarthour, getStartmin,
                        getEndhour, getEndmin, getTotal, getMemo);

            }
        });


        // 뒤로(메인페이지로) 가기
        FloatingActionButton fab_back = (FloatingActionButton) findViewById(R.id.fab_back);
        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    } //oncreate() 끝

    // 총 시간 계산해주기
    private int TimeCalculator(int start_hour, int start_min, int end_hour, int end_min){

        int Total_time;
        if(end_min < start_min){
            Total_time = (end_hour - start_hour - 1)*60 + (end_min - start_min + 60);
        } else{
            Total_time = (end_hour - start_hour)*60 + (end_min - start_min);
        }
        return Total_time;
    }


    private void writeNote(String noteId, String category, int start_hour, int start_min, int end_hour
            , int end_min, int total_time, String memo) {
        // 객체 생성
        GridNote note = new GridNote(category, start_hour, start_min, end_hour, end_min, total_time, memo);
        // 데이터베이스에 데이터 저장
        gridnote.child("notes").child(noteId).setValue(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "저장 완료",
                                Toast.LENGTH_SHORT).show();
                    }
                 })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "저장 실패",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}