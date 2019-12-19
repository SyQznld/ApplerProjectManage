package com.example.q_kang.pmsystem.ui.adpter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.Painting;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.SettingsController;


public class PagerAdapter extends RecyclePagerAdapter<PagerAdapter.ViewHolder> {

    private ViewPager viewPager;
    private Painting[] paintings;
    private SettingsController settingsController;

    public PagerAdapter(ViewPager viewPager, Painting[] paintings, SettingsController settingsController) {
        this.viewPager = viewPager;
        this.paintings = paintings;
        this.settingsController = settingsController;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup container) {
        ViewHolder holder = new ViewHolder(container);
        holder.image.getController().enableScrollInViewPager(viewPager);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        settingsController.apply(holder.image);
        Painting painting = paintings[position];
        GlideHelper.loadFull(holder.image, painting.imageId, R.mipmap.ic_launcher);

    }

    public static GestureImageView getImageView(RecyclePagerAdapter.ViewHolder holder) {
        return ((ViewHolder) holder).image;
    }


    @Override
    public int getCount() {
        return paintings.length;
    }

    public class ViewHolder extends RecyclePagerAdapter.ViewHolder {
        GestureImageView image;

        public ViewHolder(ViewGroup container) {
            super(new GestureImageView(container.getContext()));
            image = (GestureImageView) itemView;
        }
    }
}
