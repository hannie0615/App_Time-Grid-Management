package com.example.test01application;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 1 cell 을 끼울 홀더
 */
public class CellViewHolder extends RecyclerView.ViewHolder {

    // cell Layout
    TextView tv_view1;
    TextView tv_view2;
    TextView tv_view3;

    public CellViewHolder(@NonNull View itemView, CustomCellAdapter.OnItemClickListener listener) {
        super(itemView);

        // view 연결
        this.tv_view1 = itemView.findViewById(R.id.tv_view1);
        this.tv_view2 = itemView.findViewById(R.id.tv_view2);
        this.tv_view3 = itemView.findViewById(R.id.tv_view3);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    // MainActivity 에서 item 값 읽어서 쓸 수 있게 해줌
                    // listener 에 position 담아서 adapter 로 올려줌
                    if (listener != null){
                        listener.onItemClick(view, position);
                    }
                }
            }
        });
    }
}
