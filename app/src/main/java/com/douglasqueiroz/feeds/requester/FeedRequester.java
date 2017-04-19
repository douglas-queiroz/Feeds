package com.douglasqueiroz.feeds.requester;

import com.douglasqueiroz.feeds.model.Feed;
import com.douglasqueiroz.feeds.requester.converter.ConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by @douglas
 */

public class FeedRequester {

    public void getFeed(final String url, final RequesterListener<Feed> listener) {

        try {

            final Retrofit requester = new Retrofit.Builder()
                    .baseUrl(url + "/")
                    .addConverterFactory(new ConverterFactory())
                    .build();

            requester.create(IFeedCall.class).getFeed().enqueue(new Callback<Feed>() {

                @Override
                public void onResponse(Call<Feed> call, Response<Feed> response) {

                    if (response.isSuccessful()) {

                        Feed feed = response.body();

                        feed.setLink(url);

                        listener.onSuccess(feed);

                    } else {

                        listener.onError(new RequestException(response.errorBody().toString()));

                    }
                }

                @Override
                public void onFailure(Call<Feed> call, Throwable t) {

                    listener.onError(new RequestException(t.getMessage()));

                }
            });

        } catch (IllegalArgumentException e) {

            listener.onError(e);

        }
    }
}
