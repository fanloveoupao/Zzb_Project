package com.gseasypro.app.school;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.presenter.IndexSchoolPresenter;
import com.gseasypro.app.IndexPresenter;
import com.gseasypro.app.R;
import com.gseasypro.app.base.BaseFragment;

/**
 * Created by fan-gk on 2017/2/9.
 */

public class IndexSchoolFragment extends BaseFragment<IndexSchoolPresenter, IndexSchoolPresenter.IndexSchoolView> {

    private IndexSchoolPresenter indexSchoolPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_index_school, container, false);
    }

    @Override
    public IndexSchoolPresenter initPresenter() {
        indexSchoolPresenter = new IndexSchoolPresenter();
        return indexSchoolPresenter;
    }
}
