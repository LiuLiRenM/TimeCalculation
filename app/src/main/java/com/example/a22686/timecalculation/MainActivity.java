package com.example.a22686.timecalculation;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhon.suspensionfab.FabAttributes;
import com.azhon.suspensionfab.OnFabClickListener;
import com.azhon.suspensionfab.SuspensionFab;
import com.bumptech.glide.Glide;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFabClickListener,View.OnClickListener {

    private int flag = 0;
    private ArrayList<Fragment> fragments;
    private FragmentCardView fragmentCardView;
    private FragmentSchedule fragmentSchedule;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private List<Schedule> schedules;
    private List<Sum> sums;
    private double sum;
    private boolean flag1 = false;
    private boolean flag2 = false;
    private boolean flag3 = false;
    private Button sure;
    private Button reflash;
    private TextView text_SumTime;
    private TextView text_SumMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建数据库
        Connector.getDatabase();

        schedules = LitePal.findAll(Schedule.class);

        //给数据库添加初始值(测试用)
        //initDatabase();

        for (Schedule schedule: schedules) {
            flag = 1;
        }
        //通过flag的值来判断显示哪个界面，是暂无数据的界面还是列出数据的界面
        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        addFragment(fragmentManager,fragmentTransaction);
        switchFragment(flag);

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

        ImageView imageView1 = findViewById(R.id.imageview1);
        ImageView imageView2 = findViewById(R.id.imageview2);
        //首先调用Glide.with()方法并传入一个 context、activity或fragment参数
        //然后调用load()方法去加载图片
        //最后调用into()方法将图片设置到某个具体的ImageView中
        Glide.with(MainActivity.this).load(R.drawable.bg).into(imageView1);
        Glide.with(MainActivity.this).load(R.drawable.bg1).into(imageView2);

        reflash();
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                Intent intent = new Intent(MainActivity.this,MainActivity1.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        //一个可以展示多个悬浮按钮的菜单
        SuspensionFab fabTop = findViewById(R.id.fab_top);
        //添加计算图标
        FabAttributes calculate = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.drawable.calculate))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(1)
                .build();
        //添加添加图标
        FabAttributes plus = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#FF9800"))
                .setSrc(getResources().getDrawable(R.drawable.plus))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(2)
                .build();
        fabTop.addFab(calculate,plus);
        fabTop.setFabClickListener(this);

        sure = findViewById(R.id.button);
        reflash = findViewById(R.id.reflash);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.editText);
                Toast.makeText(MainActivity.this,"sure",Toast.LENGTH_SHORT).show();
                sums = LitePal.findAll(Sum.class);

                if(TextUtils.isEmpty(editText.getText()) == false) {
                    double price = Double.valueOf(editText.getText().toString());
                    //如果sums为空，将sum添加进数据库中否则更新id为1的数据
                    if(sums.isEmpty()) {
                        Sum sum_db = new Sum();
                        sum_db.setPrice(price);
                        sum_db.save();
                        multiplication();
                        reflash();
                    }
                    else {
                        ContentValues values = new ContentValues();
                        values.put("price",price);
                        LitePal.update(Sum.class,values,1);
                        multiplication();
                        reflash();
                    }
                }

            }
        });
        reflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sum();
                Toast.makeText(MainActivity.this,"reflash",Toast.LENGTH_SHORT).show();
                multiplication();
                reflash();
            }
        });

        //打开键盘时，将fab浮到键盘上面
        AndroidBug5497Workaround.assistActivity(this);

        //设置cardview透明度
        CardView cardView = findViewById(R.id.card_price);
        cardView.setAlpha((float)0.5);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



//    private void initDatabase() {
//
//        for(int i = 0; i < 10; i++) {
//            Schedule schedule = new Schedule();
//            schedule.setDate("2018-07-01");
//            double text1 = 4.5;
//            schedule.setMorningtime(text1);
//            schedule.setAfternoontime(text1);
//            schedule.setNighttime(text1);
//            schedule.setRemakestime(text1);
//            schedule.save();
//        }
//
//    }

    //添加fragment到arraylist中
    private void addFragment(FragmentManager fragmentManager,FragmentTransaction fragmentTransaction) {
        fragments = new ArrayList<>();
        fragmentCardView = new FragmentCardView();
        fragmentSchedule = new FragmentSchedule();
        fragments.add(fragmentCardView);
        fragments.add(fragmentSchedule);
        fragmentTransaction.add(R.id.fl_container,fragmentCardView);
        fragmentTransaction.add(R.id.fl_container,fragmentSchedule);
        fragmentTransaction.commit();
    }

    //根据index选择显示以及隐藏对应的fragment
    public void switchFragment(int index) {
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for(int i = 0; i < fragments.size(); i++){
            if (i == index){
                fragmentTransaction.show(fragments.get(index));
            } else {
                fragmentTransaction.hide(fragments.get(i));
            }
        }
        fragmentTransaction.commit();
    }

    //fab的监听事件
    @Override
    public void onFabClick(FloatingActionButton fab, Object tag) {
        if (tag.equals(1)) {
            //calculate 计算工作总时间和工资的按钮
            sum();
            for (Sum sum : sums) {
                String sum1 = String.valueOf(sum());
                multiplication();
                reflash();
                Toast.makeText(MainActivity.this,sum1,Toast.LENGTH_SHORT).show();
            }


        }else if (tag.equals(2)){
            //plus 添加数据到数据库的按钮
            Intent intent = new Intent(MainActivity.this,MainActivity1.class);
                startActivity(intent);
                finish();
        }
    }

    //计算总时间
    private double sum() {
        sums = LitePal.findAll(Sum.class);
        sum = 0;

        if (schedules.isEmpty() != true) {
            for (Schedule schedule: schedules) {
                sum = sum + schedule.getMorningtime() + schedule.getAfternoontime() + schedule.getNighttime()
                        + schedule.getRemakestime();

            }

            //如果sum这张表为空，则将sum的值添加进数据表sum中，否则更新sum这张表的id为1的数据
            if(sums.isEmpty() == true) {
                Sum sum_db = new Sum();
                sum_db.setTimeSum(sum);
                sum_db.save();
            }
            else {
                ContentValues values = new ContentValues();
                values.put("timesum",sum);

                LitePal.update(Sum.class,values,1);
            }
        }


        return sum;
    }

    @Override
    public void onClick(View view) {

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

    //乘法，计算总工资
    private void multiplication() {
        sums = LitePal.findAll(Sum.class);

        if(sums.isEmpty() != true) {
            for (Sum sum : sums) {
                double mul = sum.getPrice() * sum.getTimeSum();
                sum.setMoeneySum(mul);
                ContentValues values = new ContentValues();
                values.put("moneysum",mul);

                LitePal.update(Sum.class,values,1);
            }
        }

    }

    //刷新总时间和总工资
    private void reflash() {
        sums = LitePal.findAll(Sum.class);

        text_SumMoney = findViewById(R.id.text_SumMonery);
        text_SumTime = findViewById(R.id.text_SumTime);
        if(sums.isEmpty() != true) {
            for (Sum sum : sums) {
                String time = "当前已工作了" + String.valueOf(sum.getTimeSum()) + "个小时";
                text_SumTime.setText(time);
                String money = "当前的工资为" + String.valueOf(sum.getMoeneySum()) + "元";
                text_SumMoney.setText(money);
            }
        }
        else {
            String time = "当前工作时间为0个小时";
            String money = "当前的工资为0元";
            text_SumTime.setText(time);
            text_SumMoney.setText(money);
        }
    }
}
