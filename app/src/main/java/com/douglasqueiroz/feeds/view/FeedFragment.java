package com.douglasqueiroz.feeds.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.douglasqueiroz.feeds.R;
import com.douglasqueiroz.feeds.model.Feed;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @douglas
 */

public class FeedFragment extends Fragment {

    @BindView(R.id.list_view)
    public ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        ButterKnife.bind(this, view);
    }

    public void setFeed(Feed feed) {
        listView.setAdapter(new ItemAdapter(getActivity(), feed.getItemList()));
    }
}
