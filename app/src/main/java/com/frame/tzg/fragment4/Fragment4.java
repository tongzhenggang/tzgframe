package com.frame.tzg.fragment4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frame.tzg.R;

import butterknife.ButterKnife;

/**
 * Created by tzg on 2017/2/17.
 */

public class Fragment4 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4_layout, null);
        ButterKnife.bind(this,view);

        return view;
    }

   /* @OnClick(R.id.tv)
    public void onClick() {

        new WebCoreAction(getActivity(),"http://qq.com");
    }*/
}
