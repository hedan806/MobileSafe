package com.hedan.mobilesafe.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hedan.mobilesafe.R;

/**
 * Created by Administrator on 2015/11/8.
 */
public class ToolbarHelper {

    private Context mContext;
    private FrameLayout mContentView;
    private View mUserView;
    private Toolbar toolbar;
    private LayoutInflater mInflater;

    /**
     * 两个属性
     * 1、toolbar是否悬浮在窗口之上
     * 2、Toolbar的高度获取
     */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public ToolbarHelper( Context context,int layoutResID) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        /*初始化整个内容*/
        initContentView();
        /*初始化用户定义的布局*/
        initUserView(layoutResID);
        /*初始化Toolbar*/
        initToolbar();
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        View view = mInflater.inflate(R.layout.base_toolbar,mContentView);
        toolbar = (Toolbar) view.findViewById(R.id.id_base_toolbar);
    }

    /**
     * 初始化用户界面
     * @param layoutResID
     */
    private void initUserView(int layoutResID) {
        mUserView = mInflater.inflate(layoutResID,null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中设置的悬浮标志*/
        boolean overly = typedArray.getBoolean(0,false);
        /*获取主题中定义的toolbar高度*/
        int toolBarSize = (int) typedArray.getDimension(1,(int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不要设置间距*/
        params.topMargin = overly ? 0 : toolBarSize;
        mContentView.addView(mUserView, params);
    }


    /**
     * 初始化整个内容
     */
    private void initContentView() {
        /*直接创建一个帧布局，作为视图容器的父容器*/
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    public FrameLayout getContentView(){
        return mContentView;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
