package com.douglasqueiroz.feeds.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.douglasqueiroz.feeds.R;
import com.douglasqueiroz.feeds.model.Feed;
import com.douglasqueiroz.feeds.model.Item;
import com.douglasqueiroz.feeds.requester.FeedRequester;
import com.douglasqueiroz.feeds.requester.RequesterListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @douglas
 */

public class FeedFragment extends Fragment implements AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    final FeedRequester feedRequester = new FeedRequester();

    @BindView(R.id.list_view)
    public ListView listView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

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

        refreshLayout.setOnRefreshListener(this);

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

    @Override
    public void onRefresh() {

        refreshLayout.setRefreshing(true);

        feedRequester.getFeed(feed.getLink(), new RequesterListener<Feed>() {

            @Override
            public void onSuccess(Feed feed) {

                setFeed(feed);

                refreshLayout.setRefreshing(false);

            }

            @Override
            public void onError(Exception e) {

                refreshLayout.setRefreshing(false);

            }

        });
    }
}
