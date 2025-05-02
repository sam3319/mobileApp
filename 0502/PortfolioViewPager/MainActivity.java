package kr.co.company.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    String[] titles = {"아두이노 오르골 프로젝트", "카페 키오스크 프로젝트", "nodeMCU 비트코인 차트 프로젝트"};
    int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter(MainActivity.this, images, titles);
        viewPager.setAdapter(myPagerAdapter);
    }
}
