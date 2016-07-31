package fragment;

import android.view.View;
import android.widget.TextView;

public class GameFragment extends BaseFragment {

	@Override
	protected LoadResult load() {
		return BaseFragment.LoadResult.error;
	}

	@Override
	public View createSuccessView() {
		TextView view=new TextView(getActivity());
		view.setText("我是GameFragment");
		return view;
	}
}
