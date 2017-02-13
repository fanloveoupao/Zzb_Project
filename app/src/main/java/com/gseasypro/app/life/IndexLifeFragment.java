package com.gseasypro.app.life;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.presenter.IndexLearnPresenter;
import com.example.presenter.IndexLifePresenter;
import com.gseasypro.app.R;
import com.gseasypro.app.base.BaseFragment;

/**
 * Created by fan-gk on 2017/2/9.
 */

public class IndexLifeFragment extends BaseFragment<IndexLifePresenter,IndexLifePresenter.IndexLifeIView> {

    private IndexLifePresenter indexLifePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_index_life, container, false);
    }

    @Override
    public IndexLifePresenter initPresenter() {
        indexLifePresenter=new IndexLifePresenter();
        return indexLifePresenter;
    }
}
