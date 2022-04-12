package com.example.myapplication;


import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {
    private ArrayList<MainData> arrayList;
    private SparseBooleanArray selectedItems=new SparseBooleanArray();
    private int prePosition=-1;
    private Context context;

    public MainAdapter(ArrayList<MainData> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent,false);

        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
//        holder.title_id.setText(arrayList.get(position).getTitle_id());
        holder.id.setText(String.valueOf(arrayList.get(position).getId())+".");
        holder.content.setText(arrayList.get(position).getContent());
        holder.itemView.setTag(position);
        holder.onBind(position,selectedItems);
        holder.setOnViewHolderItemClickListener(new OnViewHolderItemClickListener() {
            @Override
            public void onViewHolderItemClick() {
                if (selectedItems.get(position)) {
                    // 펼쳐진 Item을 클릭 시
                    selectedItems.delete(position);
                } else {
                    // 직전의 클릭됐던 Item의 클릭상태를 지움
                    selectedItems.delete(prePosition);
                    // 클릭한 Item의 position을 저장
                    selectedItems.put(position, true);
                }
                // 해당 포지션의 변화를 알림
                if (prePosition != -1) notifyItemChanged(prePosition);
                notifyItemChanged(position);
                // 클릭된 position 저장
                prePosition = position;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.id.getText().toString();
                Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition());

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {

        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title_id;
        protected TextView id;
        protected TextView content;
        protected EditText description;
        OnViewHolderItemClickListener onViewHolderItemClickListener;

        public CustomViewHolder( View itemView) {
            super(itemView);
//            this.title_id = (TextView) itemView.findViewById(R.id.profile);
            this.id = (TextView) itemView.findViewById(R.id.name);
            this.content = (TextView) itemView.findViewById(R.id.name2);
            this.description=(EditText)itemView.findViewById(R.id.descripstion);

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onViewHolderItemClickListener.onViewHolderItemClick();
                }
            });

        }

        public void onBind(int position, SparseBooleanArray selectedItems){

            changeVisibility(selectedItems.get(position));
        }
        private void changeVisibility(final boolean isExpanded) {
            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 600) : ValueAnimator.ofInt(600, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(500);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // imageView의 높이 변경
                    description.getLayoutParams().height = (int) animation.getAnimatedValue();
                    description.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    description.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
        public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener onViewHolderItemClickListener) {
            this.onViewHolderItemClickListener = onViewHolderItemClickListener;
        }
    }
}
