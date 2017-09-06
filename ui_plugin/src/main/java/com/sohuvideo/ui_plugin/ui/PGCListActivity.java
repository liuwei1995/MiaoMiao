package com.sohuvideo.ui_plugin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sohu.lib.net.parse.CommonDataParser;
import com.sohu.lib.net.request.DataRequest;
import com.sohu.lib.net.request.listener.DataResponseListener;
import com.sohu.lib.net.util.ErrorType;
import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.adapter.PgcListAdapter;
import com.sohuvideo.ui_plugin.control.ResponseDataWrapperSet;
import com.sohuvideo.ui_plugin.fragment.ChannelFragment;
import com.sohuvideo.ui_plugin.manager.NetRequestManager;
import com.sohuvideo.ui_plugin.model.PgcListData;
import com.sohuvideo.ui_plugin.net.URLFactory;
import com.sohuvideo.ui_plugin.utils.LogManager;
import com.sohuvideo.ui_plugin.view.HeaderPullListView;

/**
 * MORE
 */
public class PGCListActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = PGCListActivity.class.getSimpleName();
    private static final int COUNT_BY_PAGE=20;
    private HeaderPullListView mListView;
    private TextView mTitleTv;
    private PgcListAdapter adapter;
    private PgcListData mCurrentPgcs;
    private PgcListData mAllPgcs = new PgcListData();
    private ImageView mBakcIv;
    private String pgcUrl;
    private View mFooterView;
    private Boolean isLoadingMore = false;
    private View mLoadingView;
    private View mEndView;
    private int mCurrentPage = 1;
    /*是否是下拉刷新*/
    private boolean isPullDown = true;
    private int cid;
    private int second_cate_code;
    private String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentPage = savedInstanceState.getInt("mCurrentPage");
        }
        setContentView(R.layout.activity_pgclist);
        handleIntent();
        setUpView();
        initEvent();
        loadDataFromNet();
    }

    private void handleIntent() {
        Intent mIntent = getIntent();
        if(mIntent!=null){
            cid =mIntent.getIntExtra(ChannelFragment.CID,0);
            second_cate_code = mIntent.getIntExtra(ChannelFragment.SECOND_CATE_CODE, 0);
            mTitle = mIntent.getStringExtra(ChannelFragment.TITLE);
            LogManager.e(TAG, cid + "");
            LogManager.e(TAG, second_cate_code + "");
            LogManager.e(TAG, mTitle + "");
        }
    }


    /**
     * 初始化时事件
     */
    private void initEvent() {
        mBakcIv.setOnClickListener(this);
        mTryBt.setOnClickListener(this);
    }

    /**
     * 初始化界面
     */
    private void setUpView() {
        mTitleTv = (TextView) findViewById(R.id.id_pgc_title);
        mListView = (HeaderPullListView) findViewById(R.id.id_home_lv);
        mFooterView = LayoutInflater.from(this).inflate(
                R.layout.listview_footer_end_channel, null);
        mListView = (HeaderPullListView) findViewById(R.id.id_home_lv);
        mListView.addFooterView(mFooterView);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view == null)
                    return;

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() + 1 == view.getCount()) {
                        if (isLoadingMore) {
                            return;
                        }
                        mFooterView.setVisibility(View.VISIBLE);
                        isPullDown = false;
                        loadDataFromNet();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (mListView != null) {
                    mListView.setFirstItemIndex(firstVisibleItem);
                }
                if (firstVisibleItem > 0) {
                    if (mFooterView != null) {
                        mFooterView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mListView.setonRefreshListener(new HeaderPullListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                isPullDown = true;
                loadDataFromNet();
            }
        });
        resertState();
        showLoadingView();

        mBakcIv = (ImageView) findViewById(R.id.id_back_iv);
        mTitleTv.setText(mTitle);
    }

    /**
     * 获取网络数据 每次加载20条 COUNT_BY_PAGE
     *
     * @return
     */
    private PgcListData loadDataFromNet() {
        isLoadingMore = true;
        setLoadingView();
        if (isPullDown) { //如果是下拉刷新
            //直接刷新第一页数据
            mCurrentPage = 1;
        } else {
            mCurrentPage++;
        }
        //取对应页的数据
        pgcUrl = URLFactory.getRefreshPgcUrl(cid, second_cate_code, mCurrentPage,COUNT_BY_PAGE);
        DataRequest dataRequest = new DataRequest(pgcUrl);
        LogManager.e(TAG, pgcUrl + "");
        dataRequest.setResultParser(new CommonDataParser(ResponseDataWrapperSet.PgcListDataWrapper.class));
        NetRequestManager.getRequestManager().startDataRequestAsync(dataRequest, new DataResponseListener() {
            @Override
            public void onSuccess(Object o, boolean b) {
                LogManager.e(TAG, "获取当" + mCurrentPage + "页数据成功");
                ResponseDataWrapperSet.PgcListDataWrapper wrapper = (ResponseDataWrapperSet.PgcListDataWrapper) o;
                mCurrentPgcs = wrapper.getData(); //本次获取的数据
                if (mAllPgcs.getVideos() != null
                        && mAllPgcs.getVideos().size() >= mCurrentPgcs
                        .getCount()) {
                    setNoMoreLoadingDataView(); //没有数据显示底部"已经到底了"
                    mListView.onRefreshComplete();
                    return;
                }
                if (mCurrentPgcs != null && mCurrentPgcs.getCount() > 0
                        && mCurrentPgcs.getVideos() != null) {
                    if (isPullDown) { //下拉刷新
                        LogManager.e(TAG, "下拉刷新");
                        adapter = new PgcListAdapter(PGCListActivity.this);
                        mListView.setAdapter(adapter);
                        mAllPgcs = mCurrentPgcs;
                        adapter.add(mAllPgcs.getVideos());
                    } else {
                        LogManager.e(TAG, "滚动加载");
                        mAllPgcs.getVideos().addAll(
                                mCurrentPgcs.getVideos()); //记录当前总共多少个 来判断是否加载完毕
                        adapter.add(mCurrentPgcs.getVideos()); //只增加当前请求回来的
                    }
                }
                isLoadingMore = false;
                System.out.println(mCurrentPage);
                mListView.onRefreshComplete();
                resertState();
                mListView.setVisibility(View.VISIBLE);

                if (mAllPgcs.getVideos() != null
                        && mAllPgcs.getVideos().size() >= mCurrentPgcs
                        .getCount()) {  //防止数据太少
                    setNoMoreLoadingDataView(); //没有数据显示底部"已经到底了"
                    mListView.onRefreshComplete();
                    isLoadingMore = true;
                    return;
                }
            }

            @Override
            public void onFailure(ErrorType errorType) {
                resertState();
                showNetErrorView();
                mCurrentPage--; //失败页数减1
            }

            @Override
            public void onCancelled() {
                //TODO 请求取消
            }

        });

        return mCurrentPgcs;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_back_iv) {
            finish();
        } else if (id == R.id.id_try_bt) {
            resertState();
            showLoadingView();
            loadDataFromNet();
            LogManager.e(TAG, "reload");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCurrentPage", mCurrentPage);
    }

    /**
     * 重置状态
     */
    private void resertState() {
        hideNetErrorView();
        hideLoadingView();
        mListView.setVisibility(View.GONE);
    }

    /**
     * 显示正在加载的View
     */
    private void setLoadingView() {
        mLoadingView = mFooterView.findViewById(R.id.more_loading);
        mEndView = mFooterView.findViewById(R.id.more_end);
        mLoadingView.setVisibility(View.VISIBLE);
        mEndView.setVisibility(View.GONE);
    }

    /**
     * 已经没有更多数据
     */
    private void setNoMoreLoadingDataView() {
        mLoadingView = mFooterView.findViewById(R.id.more_loading);
        mEndView = mFooterView.findViewById(R.id.more_end);
        mLoadingView.setVisibility(View.GONE);
        mEndView.setVisibility(View.VISIBLE);
    }
}
