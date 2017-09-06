package com.sohuvideo.ui_plugin.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

import com.sohu.lib.net.parse.CommonDataParser;
import com.sohu.lib.net.request.DataRequest;
import com.sohu.lib.net.request.listener.DefaultDataResponse;
import com.sohu.lib.net.util.ErrorType;
import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.adapter.SoHuMakeAdapter;
import com.sohuvideo.ui_plugin.control.ResponseDataWrapperSet;
import com.sohuvideo.ui_plugin.manager.NetRequestManager;
import com.sohuvideo.ui_plugin.model.SoHuProduceListData;
import com.sohuvideo.ui_plugin.net.URLFactory;
import com.sohuvideo.ui_plugin.utils.LogManager;
import com.sohuvideo.ui_plugin.view.HeaderPullListView;

/**
 * 搜狐出品
 */
public class SohuMakeChannelFragment extends BaseFragment {
    private static final String TAG = SohuMakeChannelFragment.class.getSimpleName();
    private static final int COUNT_BY_PAGE = 20;
    private SoHuMakeAdapter mAdapter;
    private int mCurrentPage = 1;
    /*是否是下拉刷新*/
    private boolean isPullDown = true;
    private boolean isLoadingMore = false;

    private SoHuProduceListData mAllProduce = new SoHuProduceListData();
    private SoHuProduceListData mCurrentProduce;

    public SohuMakeChannelFragment() {

    }

    @Override
    public void loadDataFromNet() {
        isLoadingMore = true;
        showLoadingView();
        if (isPullDown) {
            mCurrentPage = 1;
        } else {
            mCurrentPage++;
        }
        DataRequest dataRequest = new DataRequest(URLFactory.getSoHUMakeUrl(9004, mCurrentPage, COUNT_BY_PAGE));
        LogManager.e(TAG, "SohuMake--loadDataFromNet--URL=" + URLFactory.getSoHUMakeUrl(9004, mCurrentPage, COUNT_BY_PAGE));
        dataRequest.setResultParser(new CommonDataParser(ResponseDataWrapperSet.SoHuProduceListDataWrapper.class));
        DefaultDataResponse response = new DefaultDataResponse() {
            @Override
            public void onSuccess(Object o, boolean b) {
                ResponseDataWrapperSet.SoHuProduceListDataWrapper wrapper = (ResponseDataWrapperSet.SoHuProduceListDataWrapper) o;
                if (updateUi(wrapper)) return;
                isLoadingMore = false;
                mListView.onRefreshComplete();
                stateReset();
                showLv();
            }

            @Override
            public void onFailure(ErrorType errorType) {
                LogManager.e(TAG, errorType.toString());
                mCurrentPage--; //失败页数减1
                stateReset();
                showErrorView();
            }
        };
        NetRequestManager.getRequestManager().startDataRequestAsync(dataRequest, response);
    }

    private boolean updateUi(ResponseDataWrapperSet.SoHuProduceListDataWrapper wrapper) {
        mCurrentProduce = wrapper.getData();
        if (mAllProduce.getVideos() != null
                && mAllProduce.getVideos().size() >= mCurrentProduce
                .getCount()) {
            setNoMoreLoadingDataView();
            mListView.onRefreshComplete();
            return true;
        }
        if (mCurrentProduce != null && mCurrentProduce.getCount() > 0
                && mCurrentProduce.getVideos() != null) {
            if (isPullDown) { //下拉刷新
                LogManager.e(TAG, "下拉刷新");
                mAdapter = new SoHuMakeAdapter(getActivity());
                mListView.setAdapter(mAdapter);
                mAllProduce = mCurrentProduce;
                mAdapter.addDatas(mAllProduce.getVideos());
            } else {
                LogManager.e(TAG, "滚动加载");
                mAllProduce.getVideos().addAll(
                        mCurrentProduce.getVideos()); //记录当前总共多少个 来判断是否加载完毕
                mAdapter.addDatas(mCurrentProduce.getVideos()); //只增加当前请求回来的
            }
        }
        return false;
    }

    @Override
    public void initListViewEvent() {
        {
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
        }
    }

    @Override
    public void initFootView() {
        mFooterView = LayoutInflater.from(getActivity()).inflate(
                R.layout.listview_footer_end_sohumake, null);
    }

}
