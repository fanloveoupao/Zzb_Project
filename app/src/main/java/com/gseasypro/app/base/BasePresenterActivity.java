package com.gseasypro.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.BasePresenter;
import com.example.IView;

/**
 * Created by fan-gk on 2017/2/3.
 */

public abstract class BasePresenterActivity<T extends BasePresenter<V>, V extends IView> extends BaseActivity {

    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }

    // 实例化presenter
    public abstract T initPresenter();
    protected abstract void initView(@Nullable Bundle savedInstanceState);
}

