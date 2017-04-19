package com.douglasqueiroz.feeds.requester;

import com.douglasqueiroz.feeds.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by @douglas
 */

interface IFeedCall {

    @GET("./")
    Call<Feed> getFeed();
}
