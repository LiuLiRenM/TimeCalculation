package com.example.a22686.timecalculation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class FragmentSchedule extends Fragment {

    private List<Schedule> scheduleList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule,container,false);
        cyclerview(view);
        return view;

    }
    //实现recyclerView的内容
    private void cyclerview(View view) {
        initSchedule();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        ScheduleAdapter adapter = new ScheduleAdapter(scheduleList);
        adapter.setOnItemListener(new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(),"你点击了第" + position + "个item",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

    }
    private void initSchedule() {
        scheduleList.clear();
        List<Schedule> schedules = LitePal.findAll(Schedule.class);
        for (Schedule schedule: schedules) {
            scheduleList.add(schedule);
        }
    }

}
