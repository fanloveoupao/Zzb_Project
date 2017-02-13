package com.gseasypro.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.IView;

import app.gseasypro.com.utils.executor.ThreadExecutor;
import app.gseasypro.com.utils.ui.KeyBoardUtils;
import app.gseasypro.com.utils.ui.widget.IIntentInterceptor;

/**
 * Created by fan-gk on 2017/2/9.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void launch(final Intent intent, final IIntentInterceptor intentInterceptor, final boolean finish) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                if (intentInterceptor != null)
                    intentInterceptor.intercept(intent);
                BaseActivity.this.startActivity(intent);
                if (finish)
                    finish();
            }
        });
    }

    public void launch(String action, final boolean finish) {
        Intent intent = new Intent(action);
        launch(intent, null, finish);
    }

    public void launch(final Class<?> activity, final boolean finish) {
        launch(activity, null, finish);
    }

    public void launch(Class<?> activity, IIntentInterceptor intentInterceptor, boolean finish) {
        Intent intent = new Intent(BaseActivity.this, activity);
        launch(intent, intentInterceptor, finish);
    }

    public void launch(final Class<?> activity, final IIntentInterceptor intentInterceptor) {
        Intent intent = new Intent(BaseActivity.this, activity);
        launch(intent, intentInterceptor, false);
    }

    public void launchForResult(final Class<?> activity, final int requestCode) {
        launchForResult(activity, requestCode, null);
    }

    public void launchForResult(final Class<?> activity, final int requestCode, final IIntentInterceptor intentInterceptor) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BaseActivity.this, activity);
                if (intentInterceptor != null)
                    intentInterceptor.intercept(intent);
                BaseActivity.this.startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    public void hideKeyBoard() {
        KeyBoardUtils.hideKeyBoard(this);
    }
}
