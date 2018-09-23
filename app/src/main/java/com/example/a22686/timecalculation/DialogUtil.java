package com.example.a22686.timecalculation;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
这个class不是我写的，而是网上的
目的是实现自定义对话框
 */
public class DialogUtil {

    private AlertDialog dlg;
    private ImageView ivIcon;
    private TextView tvText;
    private Button btnCancel,btnSure;

    private Context context;
    private int imgResId = 0;
    //private String text;
    private DialogButtonListener1 listener1;
    private DialogButtonListener2 listener2;


//    public void show(String text, final DialogButtonListener1 listener) {
//        this.context = MainActivity1.getInstance();
//        //this.text = text;
//        this.listener = listener;
//        createDialog();
//        setValue();
//    }

    public void show1(final DialogButtonListener1 listener1) {
        this.context = MainActivity1.getInstance();
        //this.text = text;
        this.listener1 = listener1;
        //this.imgResId = imgResId;
        createDialog1();
        setValue1();
    }

    public void show2(final DialogButtonListener2 listener2) {
        this.context = MainActivity1.getInstance();
        //this.text = text;
        this.listener2 = listener2;
        createDialog2();
        setValue2();
    }

//    public void show(Context context, final DialogButtonListener1 listener) {
//        this.context = context;
//        //this.text = text;
//        this.listener = listener;
//        //this.imgResId = imgResId;
//        createDialog();
//        setValue();
//    }

    //创建Dialog、初始化控件
    private void createDialog1() {
        dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        Window window = dlg.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_confirm);
        }
        window.setGravity(Gravity.CENTER);//居中
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//背景透明
        this.btnSure = window.findViewById(R.id.btnSure);
        this.btnCancel = window.findViewById(R.id.btnCancel);

    }

    private void createDialog2() {
        dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        Window window = dlg.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog);
        }
        window.setGravity(Gravity.CENTER);//居中
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//背景透明
        this.btnSure = window.findViewById(R.id.btnSure2);
    }
    //设置控件值
    private void setValue1() {
//        if (imgResId != 0) {
//            ivIcon.setImageResource(imgResId);
//        } else {
//            ivIcon.setVisibility(View.GONE);
//        }
        //tvText.setText(text);
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.cancel();
                dlg.dismiss();
            }
        });
        this.btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.sure();
                dlg.dismiss();
            }
        });
    }

    private void setValue2() {
        this.btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener2.sure();
                dlg.dismiss();
            }
        });
    }

}
