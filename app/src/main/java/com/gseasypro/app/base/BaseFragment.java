package com.gseasypro.app.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.BasePresenter;
import com.example.IView;

import app.gseasypro.com.utils.ui.KeyBoardUtils;

/**
 * Created by fan-gk on 2017/2/4.
 */

public abstract class BaseFragment<T extends BasePresenter<V>, V extends IView> extends Fragment implements IView {

    public T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    public void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }

    // 实例化presenter
    public abstract T initPresenter();


    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void hideKeyBoard() {
        getBaseActivity().hideKeyBoard();
    }
}