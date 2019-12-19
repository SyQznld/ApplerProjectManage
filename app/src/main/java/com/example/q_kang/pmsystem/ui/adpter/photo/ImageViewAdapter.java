package com.example.q_kang.pmsystem.ui.adpter.photo;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.ImageDao;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.example.q_kang.pmsystem.ui.activity.photo.ImageShowActivity;
import com.example.q_kang.pmsystem.ui.activity.photo.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;


public class ImageViewAdapter extends BaseQuickAdapter<ImageItemBean, BaseViewHolder> {
    private ImageViewAdapter adapter;
    private RequestOptions options = new RequestOptions().centerCrop().priority(Priority.HIGH).override(500, 500);
    private Context context;
    private boolean state;
    private Intent intent;

    private static ArrayList<ImageItemBean> attribute;

    private OnImageSelectListener mSelectListener;
    private OnItemClickListener mItemClickListener;

    private int mMaxCount;
    private boolean isSingle;


    public static ArrayList<ImageItemBean> getAttribute() {
        return attribute;
    }


    public ImageViewAdapter(Context context, boolean state, @Nullable List<ImageItemBean> data) {
        super(R.layout.image_view_item, data);
        this.context = context;
        adapter = this;
        this.state = state;
        attribute = new ArrayList<>();
        Log.i(TAG, "ImageViewAdapter: " + data);


        intent = new Intent(context, ImageShowActivity.class);
        intent.putExtra("flag", "bean");
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final ImageItemBean item) {

        final ImageView imageView = helper.getView(R.id.iv_image_view);
        ImageView iv_video = helper.getView(R.id.iv_video);

        if (item.getImagePath().endsWith(".mp4")) {
            Log.i(TAG, "convert: " + item.getImagePath());
            Glide.with(context).load(item.getImagePath()).apply(options).into(imageView);
            iv_video.setVisibility(View.VISIBLE);
            iv_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra("video", item.getImagePath());
                    context.startActivity(intent);
                }
            });
        } else if (item.getImagePath().endsWith(".jpg") || item.getImagePath().endsWith(".png") || item.getImagePath().endsWith(".jpeg")) {
            iv_video.setVisibility(View.INVISIBLE);
            Glide.with(context).load(item.getImagePath()).apply(options).into(imageView);
        }


        helper.setOnClickListener(R.id.iv_image_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("position", helper.getPosition());
                //  context.startActivity(intent);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, imageView, "shareNames").toBundle());
            }
        });




        helper.setOnLongClickListener(R.id.iv_image_view, new View.OnLongClickListener() {

            public boolean onLongClick(View view) {

                new MaterialDialog.Builder(mContext)
                        .title("删除图片！")
                        .content("删除当前选择图片！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                    adapter.remove(helper.getLayoutPosition());
                                    ImageDao.deleteLabel(item.getId());
                                    //  CommonUtils.deleteFile(item.getImagePath());

                                } else if (which == DialogAction.NEGATIVE) {
                                }

                            }
                        }).show();
                return false;

            }
        });

//        if (state) {
//            helper.setVisible(R.id.cb_image_view, true);
//        } else {
//            helper.setVisible(R.id.cb_image_view, false);
//        }
        if (state) {
            helper.setVisible(R.id.iv_select, true);
        } else {
            helper.setVisible(R.id.iv_select, false);
        }

        ImageView iv_select = helper.getView(R.id.iv_select);
        setItemSelect(item, iv_select);

        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attribute.contains(item)) {
                    //如果图片已经选中，就取消选中
                    unSelectImage(item);
                    setItemSelect(item, iv_select);
                } else if (isSingle) {
                    //如果是单选，就先清空已经选中的图片，再选中当前图片
                    clearImageSelect();
                    selectImage(item);
                    setItemSelect(item, iv_select);
                } else if (mMaxCount <= 0 || attribute.size() < mMaxCount) {
                    //如果不限制图片的选中数量，或者图片的选中数量
                    // 还没有达到最大限制，就直接选中当前图片。
                    selectImage(item);
                    setItemSelect(item, iv_select);
                }
            }
        });


//
//        helper.setOnCheckedChangeListener(R.id.cb_image_view, new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    if (attribute.size() >= 8) {
//                        Toast.makeText(context, "最多能选取8张照片", Toast.LENGTH_SHORT).show();
//                        CheckBox view = helper.getView(R.id.cb_image_view);
//                        view.setChecked(false);
//                        return;
//                    }
//                    attribute.add(item);
//                } else {
//                    attribute.remove(item);
//                }
//            }
//        });

    }

    private void setItemSelect(ImageItemBean item, ImageView iv_select) {
        if (attribute.contains(item)){
            iv_select.setImageResource(R.drawable.ic_image_select);
        }else {
            iv_select.setImageResource(R.drawable.ic_image_unselect);
        }
    }


    /**
     * 选中图片
     *
     * @param image
     */
    private void selectImage(ImageItemBean image) {
        attribute.add(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, true, attribute.size());
        }
    }

    /**
     * 取消选中图片
     *
     * @param image
     */
    private void unSelectImage(ImageItemBean image) {
        attribute.remove(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, false, attribute.size());
        }
    }


    private void clearImageSelect() {
        if (attribute != null && attribute.size() == 1) {
            int index = attribute.indexOf(attribute.get(0));
            if (index != -1) {
                attribute.clear();
                notifyItemChanged(index);
            }
        }
    }


    public interface OnImageSelectListener {
        void OnImageSelect(ImageItemBean image, boolean isSelect, int selectCount);
    }

    public interface OnItemClickListener {
        void OnItemClick(ImageItemBean image, int position);
    }
}
