package com.gseasypro.app;

import android.Manifest;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gseasypro.app.base.BasePresenterActivity;
import com.gseasypro.app.learn.IndexLearnFragment;
import com.gseasypro.app.life.IndexLifeFragment;
import com.gseasypro.app.mine.IndexMineFragment;
import com.gseasypro.app.school.IndexSchoolFragment;
import com.gseasypro.app.school.activity.SearchNewsActivity;

import app.gseasypro.com.utils.ui.widget.RadioButtonGroup;
import app.gseasypro.com.utils.ui.widget.SimplePopupWindow;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndexActivity extends BasePresenterActivity<IndexPresenter, IndexPresenter.IndexView> {
    private IndexPresenter presenter;

    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.v_titlebar)
    View titleBar;
    @BindView(R.id.tv_right_search)
    View tvSearch;
    @BindView(R.id.netword_card)
    View vNetwordCard;

    @OnClick(R.id.netword_card)
    void launchCheckNetworkSetting() {
        launch(Settings.ACTION_SETTINGS, false);
    }

    //region fragment_project_show
    private static final String INTERT_FRAGMENT_TAG_KEY = "fragment_tag";

    public static final String TAG_SCHOOL_FRAGMENT = "tag_school_fragment";
    public static final String TAG_LEARN_FRAGMENT = "tag_learn_fragment";
    public static final String TAG_LIFE_FRAGMENT = "tag_life_fragment";
    public static final String TAG_MIME_FRAGMENT = "tag_mime_fragment";
    @BindView(R.id.rdobtn_circle)
    RadioButton rdoBtnSchool;
    @BindView(R.id.rdobtn_message)
    RadioButton rdoBtnLearn;
    @BindView(R.id.rdobtn_project)
    RadioButton rdoBtnLife;
    @BindView(R.id.rdobtn_mine)
    RadioButton rdoBtnMine;
    @BindView(R.id.tv_msg_count)
    TextView tvMsgCount;
    private IndexSchoolFragment schoolFragment;
    private IndexLearnFragment learnFragment;
    private IndexLifeFragment lifeFragment;
    private IndexMineFragment mineFragment;
    private SimplePopupWindow moreMenuPopupWindow;
    private RadioButtonGroup.OnCheckedChangeListener tagCheckedChangedListener = new RadioButtonGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView) {
            FragmentTransaction mFt = getSupportFragmentManager().beginTransaction();
            Fragment currentFragment = null;
            tvSearch.setVisibility(buttonView.getId() == R.id.rdobtn_circle ? View.VISIBLE : View.GONE);
            switch (buttonView.getId()) {
                case R.id.rdobtn_circle:
                    if (schoolFragment == null) {
                        schoolFragment = new IndexSchoolFragment();
                        mFt.add(R.id.v_framelayout, schoolFragment, TAG_SCHOOL_FRAGMENT);
                    }
                    currentFragment = schoolFragment;
                    mTitleName.setText("广师校园");
                    break;
                case R.id.rdobtn_message:
                    if (learnFragment == null) {
                        learnFragment = new IndexLearnFragment();
                        mFt.add(R.id.v_framelayout, learnFragment, TAG_LEARN_FRAGMENT);
                    }
                    currentFragment = learnFragment;
                    mTitleName.setText("学习");
                    break;
                case R.id.rdobtn_project:
                    if (lifeFragment == null) {
                        lifeFragment = new IndexLifeFragment();
                        mFt.add(R.id.v_framelayout, lifeFragment, TAG_LIFE_FRAGMENT);
                    }
                    currentFragment = lifeFragment;
                    mTitleName.setText("生活");
                    break;
                case R.id.rdobtn_mine:
                    if (mineFragment == null) {
                        mineFragment = new IndexMineFragment();
                        mFt.add(R.id.v_framelayout, mineFragment, TAG_MIME_FRAGMENT);
                    }
                    currentFragment = mineFragment;
                    mTitleName.setText("我");
                    break;
            }
            if (currentFragment != null)
                showFragment(mFt, currentFragment);
            mFt.commit();
        }
    };

    @OnClick(R.id.tv_right_search)
    void launchSearchProject() {
        launch(SearchNewsActivity.class, false);
    }


    @OnClick(R.id.tv_right_plus)
    void popupAddMenu() {
        if (moreMenuPopupWindow == null)
            moreMenuPopupWindow = new SimplePopupWindow(this, R.layout.popup_index_more);
        moreMenuPopupWindow.showAsDropDown(titleBar);
        moreMenuPopupWindow.getContentView().findViewById(R.id.rl_add_friend)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moreMenuPopupWindow.dismiss();
                    }
                });
        moreMenuPopupWindow.getContentView().findViewById(R.id.rl_scan_qr_code)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moreMenuPopupWindow.dismiss();
                    }
                });
        moreMenuPopupWindow.getContentView().findViewById(R.id.rl_my_friend)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moreMenuPopupWindow.dismiss();
                    }
                });

    }

    @Override
    public IndexPresenter initPresenter() {
        presenter = new IndexPresenter();
        return presenter;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            schoolFragment = (IndexSchoolFragment) getSupportFragmentManager().findFragmentByTag(TAG_SCHOOL_FRAGMENT);
            learnFragment = (IndexLearnFragment) getSupportFragmentManager().findFragmentByTag(TAG_LEARN_FRAGMENT);
            lifeFragment = (IndexLifeFragment) getSupportFragmentManager().findFragmentByTag(TAG_LIFE_FRAGMENT);
            mineFragment = (IndexMineFragment) getSupportFragmentManager().findFragmentByTag(TAG_MIME_FRAGMENT);
        }
        RadioButtonGroup radioGroup = new RadioButtonGroup(rdoBtnSchool, rdoBtnLearn, rdoBtnLife, rdoBtnMine);
        radioGroup.setOnCheckedChangeListener(tagCheckedChangedListener);
        String tag = getIntent().getStringExtra(INTERT_FRAGMENT_TAG_KEY);
        showFragment(tag);
    }

    private void showFragment(FragmentTransaction ft, Fragment fragment) {
        if (fragment == null) return;
        showFragment(ft, schoolFragment, fragment);
        showFragment(ft, learnFragment, fragment);
        showFragment(ft, lifeFragment, fragment);
        showFragment(ft, mineFragment, fragment);
    }

    private void showFragment(FragmentTransaction ft, Fragment testFragment, Fragment targetFragment) {
        if (testFragment != null) {
            if (testFragment == targetFragment)
                ft.show(targetFragment);
            else
                ft.hide(testFragment);
        }
    }

    private void showFragment(String tag) {
        if (tag == null)
            tag = TAG_SCHOOL_FRAGMENT;
        switch (tag) {
            case TAG_LEARN_FRAGMENT:
                rdoBtnLearn.setChecked(true);
                break;
            case TAG_LIFE_FRAGMENT:
                rdoBtnLife.setChecked(true);
                break;
            case TAG_MIME_FRAGMENT:
                rdoBtnMine.setChecked(true);
                break;
            default:
                rdoBtnSchool.setChecked(true);
                break;
        }
    }

}
