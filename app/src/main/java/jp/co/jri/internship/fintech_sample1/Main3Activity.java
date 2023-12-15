package jp.co.jri.internship.fintech_sample1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Main3Activity extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // 表示ボタン
        View v = inflater.inflate(R.layout.activity_main3, container, false);
        return v;
    }
//public class Main3Activity extends Fragment {
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // レイアウト（activity_main3.xml）を表示す
//        View v = inflater.inflate(R.layout.activity_main3,container,false);

        // intentに格納されている遷移元のボタン名（buttonName）を受け取る
//        Intent intent = getIntent();
//        String buttonName = intent.getStringExtra("ButtonName");

        // アダプタ(TapPagerAdapter)を用いてタブ切り替え時のViewPager2の内容表示を制御する.
        // 遷移元のボタン名（buttonName）もアダプタに受け渡す
        //ViewPager2 pager = (ViewPager2)findViewById(R.id.pager);
        //TapPagerAdapter adapter = new TapPagerAdapter(this,buttonName);
        //pager.setAdapter(adapter);

        // TabLayoutとViewPager2を関連付ける（押下されたタブと内容表示を関連付ける）
        // TabLayout tabs = (TabLayout)findViewById(R.id.tab_layout);
        //new TabLayoutMediator(
        //      tabs,
        //    pager,
        //        (tab, position) -> tab.setText("TAB" + (position + 1))
        //).attach();
    }

