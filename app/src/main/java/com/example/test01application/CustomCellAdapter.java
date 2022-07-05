package com.example.test01application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView의 핵심!
 * list_item(아이템 레이아웃)에 데이터(는 메인에서 받을 예정)를 연결해줄 어댑터
 */
public class CustomCellAdapter extends RecyclerView.Adapter<CellViewHolder> {

    private List<GridNote> gridNoteList; // 이게 중요

    // 생성자 1
    // MainActivity 에서 Adapter 생성할 때, 데이터를 넣어줄거야.
    public CustomCellAdapter(List<GridNote> gridNoteList){
        this.gridNoteList = gridNoteList;
    }


    @NonNull
    @Override
    public CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate 시키기.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_item, parent, false);
        CellViewHolder holder = new CellViewHolder(view); // Holder 생성할 때 MainActivity가 만들어준 Listener도 같이 전해줌
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
        // item을 하나하나 보여주는(=bind) 함수
        holder.tv_view1.setText(gridNoteList.get(position).getCategory());
        holder.tv_view2.setText(gridNoteList.get(position).getMemo());
        holder.tv_view3.setText(String.valueOf(gridNoteList.get(position).getTotal_time())+" 분");

    }

    // 아이템이 몇개인지 세서 뿌려줌.
    @Override
    public int getItemCount() {
        return (gridNoteList != null ? gridNoteList.size() : 0);
    }

}
