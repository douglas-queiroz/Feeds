package com.douglasqueiroz.feeds.requester.converter;

import com.douglasqueiroz.feeds.model.Feed;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by @douglas
 */

public class ConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        return type.equals(Feed.class) ? new FeedResponseConverter() : null;

    }
}
