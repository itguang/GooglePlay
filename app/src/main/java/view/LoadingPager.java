package view;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.dell_pc.googleplay.R;

import fragment.BaseFragment;
import tools.UiUtils;

/**
 * 作者: 小光哥 on 2016/7/31.
 * 邮箱: 1445037803@qq.com
 * 修订历史:
 * 描述:
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓━━━━┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　史　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　诗　┃
 * 　　　　　　　　　┃　之　┃
 * 　　　　　　　　　┃　宠　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃BUG天敌　　　┣┓┣┓┣┓┣┓┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 */

/***
 * 创建了自定义帧布局 把baseFragment 一部分代码 抽取到这个类中
 */
public abstract class LoadingPager extends FrameLayout {

    public static final int STATE_UNKOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    public  int state = STATE_ERROR;

    private View loadingView;
    private View errorView;
    private View emptyView;

    public LoadingPager(Context context) {
        super(context);
        init();
    }

    /**
     * 在FrameLayout中 添加4种不同的界面
     */
    private void init() {
        loadingView = createLoadingView();
        if (loadingView != null) {

            this.addView(loadingView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
        errorView = createErrorView();
        if (errorView != null) {
            this.addView(errorView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

        }
        emptyView = createEmptyView();
        if (emptyView != null) {
            this.addView(emptyView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
        showPage();
        show();
    }

    /**
     * 根据不同状态显示不同的界面
     */
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(state == STATE_UNKOWN
                    || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (state == STATE_SUCCESS) {
            View successView = createSuccessView();

            if (successView != null) {
                this.addView(successView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

            }
        } else {
            if (createSuccessView() != null) {
                createSuccessView().setVisibility(View.INVISIBLE);
            }
        }

    }


    /* 创建了空的界面 */
    private View createEmptyView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_empty, null);

        return view;
    }

    /* 创建了错误界面 */
    private View createErrorView() {

        View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_error, null);
        Button button = (Button) view.findViewById(R.id.page_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        return view;
    }

    /* 创建加载中的界面 */
    private View createLoadingView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_loading, null);
        return view;

    }

    /**
     * 请求服务器
     * 根据服务器的数据 切换状态
     */
    public void show() {
        if (state == STATE_ERROR || state == STATE_EMPTY) {
//            System.out.println("返回状态"+state);
            state = STATE_LOADING;
        }
        // 请求服务器 获取服务器上数据 进行判断
        // 请求服务器 返回一个结果
        new Thread() {
            public void run() {
                SystemClock.sleep(2000);
                showPage(); // 状态改变了,重新判断当前应该显示哪个界面
                final BaseFragment.LoadResult result = load();
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            state = result.getValue();
                            System.out.println("返回状态"+state);
                            showPage(); // 状态改变了,重新判断当前应该显示哪个界面
                        }
                    }
                });
            }
        }.start();
        showPage();
    }
    /**
     * 请求服务器
     *
     * @return
     */
    public abstract BaseFragment.LoadResult load();
    /***
     * 创建成功的界面
     *
     * @return
     */
    public abstract View createSuccessView();

}
