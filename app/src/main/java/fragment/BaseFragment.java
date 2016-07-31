package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tools.ViewUtils;
import view.LoadingPager;

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
public abstract class BaseFragment extends Fragment {

    public  LoadingPager loadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (loadingPager == null) {//之前的frameLayout已经有一个爹了,
            loadingPager = new LoadingPager(getActivity()) {
                @Override
                public LoadResult load() {
                    return BaseFragment.this.load();
                }
                @Override
                public View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }
            };//new 一个真布局,显示当前页面的不同界面,根据加载状态不同,添加不同的显示界面.
        }else{
            //添加之前先把 framenlayout 的爹干掉
            ViewUtils.removeParentView(loadingPager);
        }
        return loadingPager;//拿到当前ViewPager,添加这个FrameLayout
    }
    public enum LoadResult {
        error(2), empty(3), success(4);
        int value;
        LoadResult(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    /**
     * 从服务器获取数据
     * @return
     */
    protected abstract LoadResult load();

    /**
     * 加载成功的时候
     * @return
     */
    public abstract View createSuccessView();

    public  void show(){
        if(loadingPager!=null){
            loadingPager.show();
        }

    }



}
