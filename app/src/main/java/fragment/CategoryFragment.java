package fragment;

import android.view.View;
import android.widget.TextView;

public class CategoryFragment extends BaseFragment {


    @Override
    protected LoadResult load() {
        return BaseFragment.LoadResult.error;
    }

    @Override
    public View createSuccessView() {
        TextView view = new TextView(getActivity());
        view.setText("我是CategoryFragment");
        return view;
    }
}
