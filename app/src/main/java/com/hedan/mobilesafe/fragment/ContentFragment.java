package com.hedan.mobilesafe.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hedan.mobilesafe.R;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ContentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentLayout = inflater.inflate(R.layout.content_fragment,container,false);
        return contentLayout;
    }
}
