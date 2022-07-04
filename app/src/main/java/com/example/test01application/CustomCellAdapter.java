package com.example.test01application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView의 핵심!
 * list_item(아이템 레이아웃)에 데이터(는 메인에서 받을 예정)를 연결해줄 어댑터
 */
public class CustomCellAdapter extends RecyclerView.Adapter<CellViewHolder> {

    private List<Cell> cellList = new ArrayList<>(); // 이게 중요

    // 생성자
    // MainActivity 에서 Adapter 생성할 때, 데이터를 넣어줄거야.
    public CustomCellAdapter(List<Cell> cellList){
        this.cellList = cellList;
    }

    public CustomCellAdapter() {

    }

    public interface OnItemClickListener { // MainActivity에서 구현해서 setOnItemClickListener에 넣어 쓸거야
        void onItemClick(View v, int position);
    }

    private OnItemClickListener listener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    /**
     * 자동 호출 메소드 - Holder 생성
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item.xml 을 inflate 시키기.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        CellViewHolder holder = new CellViewHolder(view, listener); // Holder 생성할 때 MainActivity가 만들어준 Listener도 같이 전해줌
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
        // item을 하나하나 보여주는(=bind) 함수
        holder.tv_view1.setText(cellList.get(position).getCell());
    }

    // 아이템이 몇개인지 세서 뿌려줌.
    @Override
    public int getItemCount() {
        return (cellList != null ? cellList.size() : 0);
    }

    // 외부에서 item을 추가시킬 함수입니다.
    void addItem(Cell cellitem) {
        cellList.add(cellitem);
    }
}
