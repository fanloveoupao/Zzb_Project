package com.gseasypro.app.learn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.presenter.IndexLearnPresenter;
import com.gseasypro.app.R;
import com.gseasypro.app.base.BaseFragment;

/**
 * Created by fan-gk on 2017/2/9.
 */
public class IndexLearnFragment extends BaseFragment<IndexLearnPresenter, IndexLearnPresenter.IndexLearnIView> {
    private IndexLearnPresenter indexLearnPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_index_learn, container, false);
    }

    @Override
    public IndexLearnPresenter initPresenter() {
        indexLearnPresenter=new IndexLearnPresenter();
        return indexLearnPresenter;
    }
}
