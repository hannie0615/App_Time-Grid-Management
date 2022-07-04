package com.example.test01application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class DaygridActivity extends AppCompatActivity {

    private TextView tv_grid;
    private List<Cell> cellList;

    private RecyclerView recyclerView;
    private CustomCellAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Button btn_apply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daygrid);

        // view 객체 연결
        tv_grid = findViewById(R.id.tv_grid);
        recyclerView = findViewById(R.id.recyclerView);
        btn_apply = findViewById(R.id.btn_apply);

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
        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);


        // db 정보를 cellList로 가져올 것!
//        cellList = new ArrayList<>();
//        cellList.add(new Cell(INTENT_Category.DO1));
//        cellList.add(new Cell(INTENT_Category.DO2));
//        cellList.add(new Cell(INTENT_Category.DO3));
//        cellList.add(new Cell(INTENT_Category.DO4));
//        cellList.add(new Cell(INTENT_Category.DO5));
        //fillCell();

        List<String> cellcategory = Arrays.asList(
                INTENT_Category.DO1,
                INTENT_Category.DO2,
                INTENT_Category.DO3,
                INTENT_Category.DO4,
                INTENT_Category.DO5
        );
        List<String> celldetail = Arrays.asList(
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다."
        );
        List<Integer> celltime = Arrays.asList(
                10, 20, 30, 40, 50
        );

        List<Integer> listResId = Arrays.asList(
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_foreground,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_foreground,
                R.drawable.ic_launcher_background
        );

        for(int i=0; i<5; i++){
            Cell cell = new Cell();
            cell.setCell(cellcategory.get(i));
            cell.setDetail(celldetail.get(i));
            cell.setTotal_time(celltime.get(i));
            cell.setImgId(listResId.get(i));

            adapter.addItem(cell);
        }// 어댑터의 값이 변경되었음.
        adapter.notifyDataSetChanged();

        // 할 일
        // '연동하기' 버튼 누르면
        // firebase 에서 category, total_time, start_here, start_min 가져와서
        // 해당하는 cellList 번호에 따라
        // cell.xml의 backgorund 옵션 컬러를 바꿔줌.
        // 가능하면 category 별로 컬러도 달랐으면 좋겠음.

        /*
         * Firebase 연동 부분 추가
         *
         * */

//        int startH = 12;
//        int startM = 20;
//        int totalTime = 30; // 30분 => 연습용 숫자
//
//        btn_apply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // input : startH, startM, totalTime
//                // output : cellList number.
//                // Function : FillCell()
//                FillCell(startH, startM, totalTime);
//            }
//        });


        // Item Click Listener 시작
        adapter.setOnItemClickListener(new CustomCellAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                if(position >= 0) {

                    String cell = cellList.get(position).getCell();
                    Toast.makeText(DaygridActivity.this, cell + "입니다.",
                            Toast.LENGTH_SHORT).show();

                    adapter.notifyItemChanged(position);
                }
            }
        });

    } // onCreate() 끝남

    private void fillCell(){
        List<String> cellcategory = Arrays.asList(
                INTENT_Category.DO1,
                INTENT_Category.DO2,
                INTENT_Category.DO3,
                INTENT_Category.DO4,
                INTENT_Category.DO5
        );
        List<String> celldetail = Arrays.asList(
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다."
        );
        List<Integer> celltime = Arrays.asList(
                10, 20, 30, 40, 50
        );

        List<Integer> listResId = Arrays.asList(
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_foreground,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_foreground,
                R.drawable.ic_launcher_background
        );

        for(int i=0; i<5; i++){
            Cell cell = new Cell();
            cell.setCell(cellcategory.get(i));
            cell.setDetail(celldetail.get(i));
            cell.setTotal_time(celltime.get(i));
            cell.setImgId(listResId.get(i));

            adapter.addItem(cell);
        }
        adapter.notifyDataSetChanged();
    }


//    private void FillCell(int start_hour, int start_min, int totalTime){
//        // input : startH, startM, totalTime
//        // output : cellList number.
//        int startcell = 0;
//        int endcell = 0;
//
//        startcell = start_hour + start_min/10;
//        endcell = startcell + totalTime/10;
//
//        for(int i=startcell; i<endcell; i++){
//            // cellList.add(new Cell(String.valueOf(i+1)));
//            // cellList.get(i).setBackgroundColor(Color.parseColor("#BDD1B0"));
//            String cell = cellList.get(i).getCell();
//
//            adapter.notifyItemChanged(i);
//        }
//
//    }

}