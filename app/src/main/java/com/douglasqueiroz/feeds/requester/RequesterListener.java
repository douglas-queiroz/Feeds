package com.douglasqueiroz.feeds.requester;

/**
 * Created by @douglas
 */

public interface RequesterListener<T> {

    void onSuccess(T type);

    void onError(Exception e);

}
