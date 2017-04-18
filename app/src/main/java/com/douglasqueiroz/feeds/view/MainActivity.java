package com.douglasqueiroz.feeds.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.douglasqueiroz.feeds.R;
import com.douglasqueiroz.feeds.model.Feed;
import com.douglasqueiroz.feeds.requester.FeedRequester;
import com.douglasqueiroz.feeds.requester.RequesterListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final Class<? extends Fragment> FEED_FRAGMENT = FeedFragment.class;

    Feed selectedFeed;

    List<Feed> feedList;

    NewFeedDialog newFeedDialog = new NewFeedDialog();

    FeedFragment feedFragment = new FeedFragment();

    Realm realm;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Realm.init(this);

        realm = Realm.getDefaultInstance();

        loadFeeds();


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, feedFragment, FEED_FRAGMENT.getSimpleName())
                .commit();
    }

    @OnClick(R.id.fab)
    public void onClickFab(View view) {
        newFeedDialog.setListner(new NewFeedDialog.NewFeedDialogListner() {
            @Override
            public void onClickSave(String url) {
                newFeedDialog.dismiss();
                addFeed(url);
            }

            @Override
            public void onClickCancel() {
                newFeedDialog.dismiss();
            }
        });
        newFeedDialog.show(getFragmentManager(), NewFeedDialog.TAG);
    }

    private void addFeed(String url) {
        final MainActivity me = this;

        new FeedRequester().getFeed(url, new RequesterListener<Feed>() {
            @Override
            public void onSuccess(Feed feed) {
                me.feedFragment.setFeed(feed);

                realm.beginTransaction();
                realm.copyToRealm(feed);
                realm.commitTransaction();

                loadFeeds();
            }

            @Override
            public void onError(Exception e) {
                String msg;
                if (e instanceof IllegalArgumentException) {
                    msg = getResources().getString(R.string.invalid_url);
                } else {
                    msg = getResources().getString(R.string.default_msg_error);
                }

                Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String title = item.getTitle().toString();

        for (Feed feed : feedList) {
            if (title.equals(feed.getTitle())) {
                setSelectedFeed(feed);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFeeds() {
        RealmQuery<Feed> query = realm.where(Feed.class);
        RealmResults<Feed> result = query.findAll();

        Menu menu = navigationView.getMenu();

        feedList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            Feed feed = result.get(i);
            if (i == 0 && selectedFeed == null) {
                setSelectedFeed(feed);
            }
            menu.add(Menu.NONE, Menu.NONE, Menu.NONE, feed.getTitle());
            feedList.add(feed);
        }
    }

    private void setSelectedFeed(Feed feed) {
        this.selectedFeed = feed;

        new FeedRequester().getFeed(feed.getLink(), new RequesterListener<Feed>() {
            @Override
            public void onSuccess(Feed feed) {
                feedFragment.setFeed(feed);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }
}
