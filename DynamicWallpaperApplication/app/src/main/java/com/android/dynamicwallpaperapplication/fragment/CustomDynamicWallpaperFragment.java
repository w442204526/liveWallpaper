package com.android.dynamicwallpaperapplication.fragment;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;

import com.android.dynamicwallpaperapplication.adapter.WallpaperListAdapter;
import com.android.dynamicwallpaperapplication.R;


public class CustomDynamicWallpaperFragment extends Fragment {


    public CustomDynamicWallpaperFragment() {

    }



    private RecyclerView recyclerView,recyclerView1;
    private WallpaperListAdapter adapter,adapter1;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_dynamic_wallpaper, container, false);

//        recyclerView = view.findViewById(R.id.wallpagelist);
//        int[] srcs={R.drawable.wallpage13,R.drawable.wallpage2,R.drawable.wallpage3,R.drawable.wallpage4,R.drawable.wallpage5,R.drawable.wallpage6,R.drawable.wallpage7,R.drawable.wallpage8,R.drawable.wallpage9,R.drawable.wallpage10,R.drawable.wallpage11,R.drawable.wallpage12};
//
//        List<WallPageBean> data = new ArrayList<>();
//        for (int i=0;i<12;i++){
//            WallPageBean wallPageBean=new WallPageBean();
//            wallPageBean.setSrc(srcs[i]);
//            wallPageBean.setText("Wallpage"+(i+1));
//            data.add(wallPageBean);
//        }

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
//        // 创建并设置布局管理器
//        recyclerView.setLayoutManager(gridLayoutManager);
//        // 创建并设置适配器
//        adapter = new WallpaperListAdapter(data, new WallpaperListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                int src = data.get(position).getSrc();
//                changeWallpaper(src);
//                Toast.makeText(getContext(),"position"+position,Toast.LENGTH_SHORT).show();
//            }
//        },getContext());
//        recyclerView.setAdapter(adapter);
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

}