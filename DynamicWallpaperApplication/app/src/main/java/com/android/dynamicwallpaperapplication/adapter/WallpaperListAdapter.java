package com.android.dynamicwallpaperapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.dynamicwallpaperapplication.bean.WallPageBean;
import com.android.dynamicwallpaperapplication.R;

import java.util.List;

public class WallpaperListAdapter extends RecyclerView.Adapter<WallpaperListAdapter.Viewholder> {

    private List<WallPageBean> data;
    private OnItemClickListener listener; // 定义接口变量
    private Context context;

    // 定义接口，用于回调点击事件
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public WallpaperListAdapter(List<WallPageBean> data, OnItemClickListener listener,Context context) {
        this.data = data;
        this.listener = listener;
        this.context=context;
    }

    class Viewholder extends RecyclerView.ViewHolder {
        private TextView textView;
        private VideoView videoView;
        private CardView cardView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.wallpage1);
            videoView=itemView.findViewById(R.id.videoView);
            cardView = itemView.findViewById(R.id.cart_view);

            // 设置ImageView的点击事件监听器
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }


    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_page_item, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.textView.setText(data.get(position).getText());

        // 获取视频的 URI
        Uri videoUri = Uri.parse("android.resource://" + data.get(position).getPackageName() + "/" + data.get(position).getSrc());
        holder.videoView.setVideoURI(videoUri);

        // 启动视频播放
        holder.videoView.setOnCompletionListener(mp -> holder.videoView.start()); // 循环播放

        // 在绑定时启动视频
        holder.videoView.start();

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}




