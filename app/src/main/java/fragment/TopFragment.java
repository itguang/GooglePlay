package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopFragment extends BaseFragment {

    @Override
    protected LoadResult load() {
        return LoadResult.error;
    }

    @Override
    public View createSuccessView() {
        TextView view = new TextView(getActivity());
        view.setText("我是TopFragment");
        return view;
    }
}
