package com.example.test01application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DaygridActivity extends AppCompatActivity {


    private TextView tv_grid;

    private RecyclerView recyclerView; // 리사이클러뷰
    private CustomCellAdapter adapter; // 어댑터
    private RecyclerView.LayoutManager layoutManager;
    private List<GridNote> gridNoteList;

    // Firebase Database 연동
    DatabaseReference database = FirebaseDatabase.getInstance().getReference(); // 데베 서버
    DatabaseReference gridnote = database.child("gridnote");

    Button btn_apply;
    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daygrid);

        // view 객체 연결
        tv_grid = findViewById(R.id.tv_grid);
        recyclerView = findViewById(R.id.recyclerView); // 리사이클러뷰 연결
        btn_apply = findViewById(R.id.btn_apply);
        gridNoteList = new ArrayList<>();


        // 뒤로(메인페이지로) 가기
        FloatingActionButton fab_back = (FloatingActionButton) findViewById(R.id.fab_back);
        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaygridActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 제목 설정
        tv_grid.setText("하루 기록 시작");
        gridNoteList.add(new GridNote("do_base",12,10,13,20,70 ,"qqqq"));
//        gridNoteList.add(new GridNote("type2", 2,2,
//                2,2,2,"oh no"));


        // '연동하기' 버튼 누르면
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Integer.toString(i++)
                readNote(Integer.toString(i++));
            }

        });

        adapter = new CustomCellAdapter(gridNoteList);
        recyclerView.setAdapter(adapter);

        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

    } // onCreate() 끝남

    private void readNote(String noteId) {

        // 데이터베이스에 데이터 저장
        gridnote.child("notes").child(noteId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
//                    GridNote gridNote = snap.getValue(GridNote.class); // 여기가 문제
//                    gridNoteList.add(gridNote);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}