package com.example.test01application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DaygridActivity<cellList> extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference(); // 데베 서버
    DatabaseReference gridnote = database.child("gridnote"); // "gridnote"라는 테이블

    private TextView tv_grid;
    private List<Cell> cellList;

    private RecyclerView recyclerView;
    private CustomCellAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Button btn_apply;
    LinearLayout item_linear;
    int i=1;


    // db 정보를 cellList로 가져올 것!

    List<String> cellcategory = new ArrayList<String>();
    List<String> celldetail = new ArrayList<String>();
    List<Integer> celltime = new ArrayList<Integer>();
    List<Integer> listResId = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daygrid);

        // view 객체 연결
        tv_grid = findViewById(R.id.tv_grid);
        recyclerView = findViewById(R.id.recyclerView);
        btn_apply = findViewById(R.id.btn_apply);
        item_linear = findViewById(R.id.item_linear);


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

        // holder 에 넣을 데이터 추가
        tv_grid.setText("하루 기록 시작");

        // Adapter 데이터 전달 => 그럼 Adapter 가 Holder 에 전해줄 것.
        adapter = new CustomCellAdapter();
        recyclerView.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        cellList = new ArrayList<>();

        cellcategory.add(INTENT_Category.DO1);
        celldetail.add("이 꽃은 국화");
        celltime.add(10);
        listResId.add(R.drawable.ic_launcher_background);




        // 할 일 : Firebase 연동하기
        // '연동하기' 버튼 누르면
        // firebase 에서 category, total_time, start_here, start_min 가져와서
        // 해당하는 cellList 번호에 따라
        // cell.xml의 backgorund 옵션 컬러를 바꿔줌.
        // 가능하면 category 별로 컬러도 달랐으면 좋겠음.
        // 확실히 여기가 문제!!
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUser(Integer.toString(i++)); // 여기만  하면 되는데

                Cell cell = new Cell();

                cell.setCell(cellcategory.get(0));
                cell.setDetail(celldetail.get(0));
                cell.setTotal_time(celltime.get(0));
                cell.setImgId(listResId.get(0));

                cellList.add(cell);
                adapter.addItem(cell);
                adapter.notifyDataSetChanged();

            }
        });

        // Item Click Listener 시작
        adapter.setOnItemClickListener(new CustomCellAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position >= 0) {
                    String cell_category = cellList.get(position).getCell();
                    Toast.makeText(DaygridActivity.this, cell_category + "입니다.",
                            Toast.LENGTH_SHORT).show();
                    adapter.notifyItemChanged(position);
                }
            }
        });

    } // onCreate() 끝남

    private void readUser(String userId) {
        //데이터 읽기
        database.child("notes").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GridNote gridNote1 = snapshot.getValue(GridNote.class);
                String cell_category = gridNote1.getCategory(); // 카테고리
                String cell_detail = gridNote1.getMemo(); // 상세 설명
                Integer cell_time = (int) gridNote1.getTotal_time(); // 총 시간

                cellcategory.add(cell_category);
                celldetail.add(cell_detail);
                celltime.add(cell_time);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { //참조에 액세스 할 수 없을 때 호출
                Toast.makeText(getApplicationContext(),"데이터를 가져오는데 실패했습니다" , Toast.LENGTH_LONG).show();
            }
        });
    }

}