package com.example.a22686.timecalculation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCardView extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cardview,container,false);
        CardView cardView = view.findViewById(R.id.cardview3);
        //设置透明度，范围0~1之间，越大，越不透明，比如说1的话，就完全不透明了
        cardView.setAlpha((float)0.5);
        return view;
    }
}
