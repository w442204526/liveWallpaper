package com.android.dynamicwallpaperapplication.fragment;

import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.Toast;

import com.android.dynamicwallpaperapplication.adapter.WallpaperListAdapter;
import com.android.dynamicwallpaperapplication.bean.WallPageBean;
import com.android.dynamicwallpaperapplication.R;
import com.android.dynamicwallpaperapplication.service.DynamicWallpaperVedio1Service;
import com.android.dynamicwallpaperapplication.service.DynamicWallpaperVedio2Service;
import com.android.dynamicwallpaperapplication.service.DynamicWallpaperVedio3Service;
import com.android.dynamicwallpaperapplication.service.DynamicWallpaperVedio4Service;
import com.android.dynamicwallpaperapplication.service.DynamicWallpaperVedio5Service;
import com.android.dynamicwallpaperapplication.service.DynamicWallpaperVedio6Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoDynamicWallpaperFragment extends Fragment {



    public VideoDynamicWallpaperFragment() {
        // Required empty public constructor
    }




    private RecyclerView recyclerView;
    private WallpaperListAdapter adapter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_dynamic_wallpaper, container, false);
        recyclerView = view.findViewById(R.id.wallpagelist);

        int[] srcs={R.raw.video1,R.raw.video10,R.raw.video3,R.raw.video4,R.raw.video5,R.raw.video6,
                R.raw.video7,R.raw.video8,R.raw.video9};

        ComponentName[] componentNames={
                new ComponentName(getContext(), DynamicWallpaperVedio1Service.class),
                new ComponentName(getContext(), DynamicWallpaperVedio2Service.class),
                new ComponentName(getContext(), DynamicWallpaperVedio3Service.class),
                new ComponentName(getContext(), DynamicWallpaperVedio4Service.class),
                new ComponentName(getContext(), DynamicWallpaperVedio5Service.class),
                new ComponentName(getContext(), DynamicWallpaperVedio6Service.class),
                new ComponentName(getContext(), DynamicWallpaperVedio1Service.class),
                new ComponentName(getContext(), DynamicWallpaperVedio2Service.class),
                new ComponentName(getContext(), DynamicWallpaperVedio3Service.class),
        };
        List<WallPageBean> data = new ArrayList<>();
        for (int i=0;i<6;i++){
            WallPageBean wallPageBean=new WallPageBean();
            wallPageBean.setSrc(srcs[i]);
            wallPageBean.setText("Wallpage"+(i+1));
            wallPageBean.setComponentName(componentNames[i]);
            wallPageBean.setPackageName(getActivity().getPackageName());
            data.add(wallPageBean);
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);

        // 创建并设置布局管理器
        recyclerView.setLayoutManager(gridLayoutManager);

        // 创建并设置适配器
        adapter = new WallpaperListAdapter(data, new WallpaperListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                int src = data.get(position).getSrc();
//                changeWallpaper(src);


                Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        data.get(position).getComponentName());
                startActivity(intent);

            }
        },getContext());



        NestedScrollView nestedScrollView = view.findViewById(R.id.nestedScrollView);
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.d("test:", "newY:" + i1); // 当前 Y 轴滚动的位置
                Log.d("test:", "oldY:" + i3);  // 之前 Y 轴滚动的位置

                // 定义改变颜色的阈值
                int threshold = 200;
                // 计算滚动的百分比
                float scrollPercentage = Math.min(1, Math.max(0, i1 / (float) threshold));

                // 定义开始颜色和结束颜色
                @ColorInt int startColor = Color.TRANSPARENT; // 起始颜色（滚动到顶部时）
                @ColorInt int endColor = ContextCompat.getColor(getContext(), R.color.deep_white); // 结束颜色（滚动超过阈值时）

                // 创建并启动颜色动画器
                ValueAnimator colorAnimator = ValueAnimator.ofArgb(startColor, endColor);
                colorAnimator.setDuration(300); // 动画持续时间
                colorAnimator.addUpdateListener(animator -> {
                    int animatedColor = (int) animator.getAnimatedValue();
                    updateStatusBarColor(animatedColor);
                });

                // 设置动画器的当前播放时间，根据滚动百分比更新
                colorAnimator.setCurrentPlayTime((long) (scrollPercentage * colorAnimator.getDuration()));
            }
        });

        return view;

    }
    // 更新状态栏颜色的方法
    private void updateStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.setStatusBarColor(color);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowInsetsController insetsController = window.getInsetsController();
                if (insetsController != null) {
                    insetsController.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    );
                }
            }
        }
    }
    private void changeWallpaper(int wallpage) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        try {
            // 使用资源中的图片更换壁纸
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), wallpage);
            wallpaperManager.setBitmap(bitmap);

            getActivity().finish();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}