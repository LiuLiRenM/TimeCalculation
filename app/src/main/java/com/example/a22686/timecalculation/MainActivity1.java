package com.example.a22686.timecalculation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.forusers.heinsinputdialogs.HeinsDatePickerDialog;
import br.com.forusers.heinsinputdialogs.interfaces.OnSelectDateListener;


public class MainActivity1 extends AppCompatActivity {

    private static MainActivity1 instance = null;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5 ;
    private EditText editText6;
    private DialogUtil dialogUtil;
    private Button btnSure1;
    private Button btnCancel;
    private Button btnSure2;
    private MainActivity mainActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity1.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*
        先通过if语句判断系统是否是5.0以上，如果是的话
        使用SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN和SYSTEM_UI_FLAG_LAYOUT_STABLE
        （注意两个Flag必须要结合在一起使用）
        表示会让应用的主体内容占用系统状态栏的空间
        最后再调用Window的setStatusBarColor()方法将状态栏设置成透明色
         */
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ImageView imageView = findViewById(R.id.image_view);
        //首先调用Glide.with()方法并传入一个 context、activity或fragment参数
        //然后调用load()方法去加载图片
        //最后调用into()方法将图片设置到某个具体的ImageView中
        Glide.with(MainActivity1.this).load(R.drawable.bg2).into(imageView);

        //hideSoftKeyboard();

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);

        btnSure1 = findViewById(R.id.btnSure);
        btnCancel = findViewById(R.id.btnCancel);
        btnSure2 = findViewById(R.id.btnSure2);

        CardView cardView6 = findViewById(R.id.cardview_6);
        cardView6.setAlpha((float)0.5);

        editText1.setInputType(InputType.TYPE_NULL); //不显示系统输入键盘
        //对edittext1的监听
        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    //弹出日期选择器
                    HeinsDatePickerDialog dialog = new HeinsDatePickerDialog();
                    dialog.setListener(new OnSelectDateListener() {
                        @Override
                        public void onSelectDate(Date date) throws Exception {
                            //Do somenthing
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String date1 = simpleDateFormat.format(date);
                            editText1.setText(date1);
                            Toast.makeText(MainActivity1.this,date1,Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show(getSupportFragmentManager(), getClass().getSimpleName());
                }
            }
        });
        //对editText1的监听
        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出日期选择器
                HeinsDatePickerDialog dialog = new HeinsDatePickerDialog();
                dialog.setListener(new OnSelectDateListener() {
                    @Override
                    public void onSelectDate(Date date) {
                        //Do somenthing
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String date1 = simpleDateFormat.format(date);
                        editText1.setText(date1);
                        Toast.makeText(MainActivity1.this,date1,Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show(getSupportFragmentManager(), getClass().getSimpleName());
            }
        });

    }

    //点击除ExitText的地方将软键盘收起
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.setFocusable(false);
                    v.setFocusableInTouchMode(true);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //将menu文件夹中的toolbar.xml加载进来
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }


    //对check图标实现监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check:
                if(TextUtils.isEmpty(editText1.getText()) == false
                        && TextUtils.isEmpty(editText2.getText()) == false
                        && TextUtils.isEmpty(editText3.getText()) == false
                        && TextUtils.isEmpty(editText4.getText()) == false
                        && TextUtils.isEmpty(editText5.getText()) == false
                        ) {
                    dialogUtil = new DialogUtil();
                    dialogUtil.show1(new DialogButtonListener1() {
                        @Override
                        public void sure() {
                            getInfo();
                            Intent intent = new Intent(MainActivity1.this,MainActivity.class);
                            startActivity(intent);

                            finish();
                        }

                        @Override
                        public void cancel() {
                            Toast.makeText(MainActivity1.this,"点击了取消",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    dialogUtil = new DialogUtil();
                    dialogUtil.show2(new DialogButtonListener2() {
                        @Override
                        public void sure() {
                            Toast.makeText(MainActivity1.this,"确定",Toast.LENGTH_SHORT).show();
                        }


                    });
                }
                break;
        }
        return true;
    }

    //为了实现自定义对话框
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        instance = this;
    }

    //为了实现自定义对话框
    public static MainActivity1 getInstance() {
        return instance;
    }

    //不需要弹出软键盘的ExitText禁止弹出软键盘
//    private void hideSoftKeyboard() {
//        final EditText editText = findViewById(R.id.editText1);
//        //禁止软键盘弹出
//        if (android.os.Build.VERSION.SDK_INT > 10) {//4.0以上 danielinbiti
//            try {
//                Class<EditText> cls = EditText.class;
//                Method setShowSoftInputOnFocus;
//                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
//                        boolean.class);
//                setShowSoftInputOnFocus.setAccessible(true);
//                setShowSoftInputOnFocus.invoke(editText, false);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        //监听editTextView获取焦点
//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    //隐藏系统软键盘
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
//                }
//            }
//        });
//    }

    //获得ExitText的内容并将其保存到数据库中
    private void getInfo() {
        Schedule schedule = new Schedule();
        schedule.setDate(editText1.getText().toString());
        String text2 = editText2.getText().toString();
        schedule.setMorningtime(Double.valueOf(text2));
        String text3 = editText3.getText().toString();
        schedule.setAfternoontime(Double.valueOf(text3));
        String text4 = editText4.getText().toString();
        schedule.setNighttime(Double.valueOf(text4));
        String text5 = editText5.getText().toString();
        schedule.setRemakestime(Double.valueOf(text5));
        schedule.setRemakes(editText6.getText().toString());
        schedule.save();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity1.this, MainActivity.class);
        startActivity(intent    );
    }


}
