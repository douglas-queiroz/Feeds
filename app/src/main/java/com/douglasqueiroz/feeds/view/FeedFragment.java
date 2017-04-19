package com.douglasqueiroz.feeds.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.douglasqueiroz.feeds.R;
import com.douglasqueiroz.feeds.model.Feed;
import com.douglasqueiroz.feeds.model.Item;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @douglas
 */

public class FeedFragment extends Fragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.list_view)
    public ListView listView;
    private Feed feed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        ButterKnife.bind(this, view);

        listView.setOnItemClickListener(this);
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
        listView.setAdapter(new ItemAdapter(getActivity(), feed.getItemList()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Item item = feed.getItemList().get(position);

        Intent intent = new Intent(getActivity(), ItemDetailsActivity.class);

        intent.putExtra(ItemDetailsActivity.ITEM, item);

        startActivity(intent);
    }
}
