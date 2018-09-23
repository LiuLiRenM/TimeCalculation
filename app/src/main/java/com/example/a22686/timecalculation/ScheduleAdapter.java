package com.example.a22686.timecalculation;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<Schedule> mScheduleList;

    private CardView cardView;

    private Context mcontext;

    private View view;

    private OnItemClickListener mOnItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView text1,text2,text3,text4,text5;
        CardView cardView1;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
            text3 = view.findViewById(R.id.text3);
            text4 = view.findViewById(R.id.text4);
            text5 = view.findViewById(R.id.text5);
            cardView1 = view.findViewById(R.id.cardView2);
        }
    }
    public ScheduleAdapter(List<Schedule> scheduleList) {
        mScheduleList = scheduleList;
    }

    //用于创建ViewHolder实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        if(mcontext == null) {
            mcontext = parent.getContext();
        }
        view = LayoutInflater.from(mcontext).
                inflate(R.layout.fragment_schedule_item,parent,false);
        cardView = view.findViewById(R.id.cardView2);
        //设置透明度，范围0~1之间，越大，越不透明，比如说1的话，就完全不透明了
        cardView.setAlpha((float) 0.5);
        return new ViewHolder(view);

    }

    //用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Schedule schedule = mScheduleList.get(position);
        holder.text1.setText(schedule.getDate());
        String text2 = Double.toString(schedule.getMorningtime());
        holder.text2.setText(text2);
        String text3 = Double.toString(schedule.getAfternoontime());
        holder.text3.setText(text3);
        String text4 = Double.toString(schedule.getNighttime());
        holder.text4.setText(text4);
        String text5 = Double.toString(schedule.getRemakestime());
        holder.text5.setText(text5);
        //添加对recycler_item的监听
        if(mOnItemClickListener != null) {
            holder.cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

    //告诉RecyclerView一共有多少子项
    @Override
    public int getItemCount() {

        return mScheduleList.size();
    }

    //添加一个OnItemClickListener接口，并且定义一个onClick方法
    public interface OnItemClickListener {
        void onClick(int position);
    }

    //定义一个监听的方法，方便主类调用
    public void setOnItemListener(OnItemClickListener onItemListener) {
        this.mOnItemClickListener = onItemListener;
    }
}

