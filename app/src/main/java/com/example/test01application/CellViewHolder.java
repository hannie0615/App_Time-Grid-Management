package com.example.test01application;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * cell_item.xml 뷰 연결
 */
public class CellViewHolder extends RecyclerView.ViewHolder {

    // cell_item.xml
    TextView tv_view1;
    TextView tv_view2;
    TextView tv_view3;
    ImageView imageView;

    public CellViewHolder(@NonNull View itemView) {
        super(itemView);

        // view 연결
        this.tv_view1 = itemView.findViewById(R.id.tv_view1);
        this.tv_view2 = itemView.findViewById(R.id.tv_view2);
        this.tv_view3 = itemView.findViewById(R.id.tv_view3);
        this.imageView = itemView.findViewById(R.id.imageView);
    }
}
