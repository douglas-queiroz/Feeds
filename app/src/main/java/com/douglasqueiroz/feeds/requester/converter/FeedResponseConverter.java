package com.douglasqueiroz.feeds.requester.converter;

import android.util.Log;

import com.douglasqueiroz.feeds.model.Feed;

import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by @douglas
 */

class FeedResponseConverter implements Converter<ResponseBody, Feed> {

    private static final String TAG = FeedResponseConverter.class.getSimpleName();

    @Override
    public Feed convert(ResponseBody response) throws IOException {
        try {

            final FeedParser parser = new FeedParser();

            SAXParserFactory.newInstance().newSAXParser().parse(new InputSource(new StringReader(response.string())), parser);

            return parser.getFeed();

        } catch (final Exception e) {

            Log.e(TAG, e.getMessage());
        }

        return null;
    }
}
