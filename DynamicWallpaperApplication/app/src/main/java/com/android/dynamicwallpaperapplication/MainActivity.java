package com.android.dynamicwallpaperapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.dynamicwallpaperapplication.adapter.ViewpaperAdapter;
import com.android.dynamicwallpaperapplication.fragment.CustomDynamicWallpaperFragment;
import com.android.dynamicwallpaperapplication.fragment.VideoDynamicWallpaperFragment;
import com.android.dynamicwallpaperapplication.service.CustomDynamicWallpaperService;
import com.android.dynamicwallpaperapplication.service.DynamicWallpaperVedio1Service;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new VideoDynamicWallpaperFragment());
        fragments.add(new CustomDynamicWallpaperFragment());

        viewPager = findViewById(R.id.viewpaper);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new ViewpaperAdapter(supportFragmentManager,fragments));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);  // 设置状态栏颜色为透明
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.deep_white));
            setAndroidNativeLightStatusBar(this);

        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    ImageView imageView1 = findViewById(R.id.video_dynami_wallpaper);
                    ImageView imageView2 = findViewById(R.id.custom_dynami_wallpaper);
                    imageView1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_select_video_dynami_wallpaper));
                    imageView2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_custom_dynamic_wallpaper));
                }else {
                    ImageView imageView3 = findViewById(R.id.video_dynami_wallpaper);
                    ImageView imageView4 = findViewById(R.id.custom_dynami_wallpaper);
                    imageView3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_video_dynami_wallpaper));
                    imageView4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_select_custom_dynamic_wallpaper));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @SuppressLint("NonConstantResourceId")
    public void startActivity(View view) {
        switch (view.getId()){
            case R.id.video_dynami_wallpaper:

                ImageView imageView1 = findViewById(R.id.video_dynami_wallpaper);
                ImageView imageView2 = findViewById(R.id.custom_dynami_wallpaper);
                imageView1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_select_video_dynami_wallpaper));
                imageView2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_custom_dynamic_wallpaper));
                viewPager.setCurrentItem(0);
                break;
            case R.id.custom_dynami_wallpaper:

                ImageView imageView3 = findViewById(R.id.video_dynami_wallpaper);
                ImageView imageView4 = findViewById(R.id.custom_dynami_wallpaper);
                imageView3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_video_dynami_wallpaper));
                imageView4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button_select_custom_dynamic_wallpaper));
                viewPager.setCurrentItem(1);
                break;
        }
    }
    public static void setAndroidNativeLightStatusBar(Activity activity) {
        boolean isDark;
        if(activity.getApplicationContext().getResources().getConfiguration().uiMode == 0x21){
            isDark = true;
        }else{
            isDark = false;
        }
        View decor = activity.getWindow().getDecorView();
        if (isDark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
    }

    public void start_custom_dynamic_wallpaper(View view) {
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(this, CustomDynamicWallpaperService.class));
        startActivity(intent);
    }
}