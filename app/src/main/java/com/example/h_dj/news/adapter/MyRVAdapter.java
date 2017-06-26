package com.example.h_dj.news.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.bean.NewsBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by H_DJ on 2017/5/16.
 * 适配器
 */

public class MyRVAdapter extends BaseRecycleViewAdapter {
    /**
     * banner
     */
    private static final int BANNER = 0;//bannerItem


    /**
     * 当前类型
     */
    private int currentType = BANNER;
    private Context mContext;
    private List<String> images;
    private OnItemListener onItemListener;

    public MyRVAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        mContext = context;
        images = new ArrayList<>();

    }

    @Override
    protected void convert(MyViewHolder holder, Object o, int position) {
        if (getItemViewType(position) == BANNER) {
            images.clear();
            for (int i = 0; i < 4; i++) {
                images.add(((NewsBean.ResultBean.DataBean) mList.get(i)).getThumbnail_pic_s());
            }
            Banner mBanner = holder.getView(R.id.new_banner);
            //设置banner样式
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置banner动画效果
            mBanner.setBannerAnimation(Transformer.Default);
            mBanner.setImages(images);
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (onItemListener != null) {
                        onItemListener.onBannerListener(position);
                    }
                }
            });
            mBanner.setDelayTime(1500);
            mBanner.start();
        } else if (getItemViewType(position) > 0) {
            NewsBean.ResultBean.DataBean dataBean = (NewsBean.ResultBean.DataBean) o;
            holder.setText(R.id.item_title, dataBean.getTitle());
            holder.setText(R.id.item_author_name, dataBean.getAuthor_name());
            holder.setText(R.id.item_date, dataBean.getDate());
            Glide.with(mContext)
                    .load(dataBean.getThumbnail_pic_s())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into((ImageView) holder.getView(R.id.item_pic));
        }
    }


    /**
     * 获取布局类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            default:
                currentType = position;
        }
        return currentType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BANNER:
                return MyViewHolder.getViewHolder(mContext, parent, R.layout.new_banner);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    public interface OnItemListener extends OnItemClickListener {
        void onBannerListener(int position);
    }

    public void setItemListener(OnItemListener onItemListener) {
        this.onItemListener=onItemListener;
    }
}
