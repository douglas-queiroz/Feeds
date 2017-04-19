package com.douglasqueiroz.feeds.requester.converter;

import android.util.Log;

import com.douglasqueiroz.feeds.model.Feed;
import com.douglasqueiroz.feeds.model.Item;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import io.realm.RealmList;


/**
 * Created by @douglas
 */

class FeedParser extends DefaultHandler {

    private static final String TAG = FeedParser.class.getSimpleName();

    private static final String CLOSE_TAG = ">";
    private static final String TAG_PARAGRAPHY_END = "<p";
    private static final String TAG_PARAGRAPHY_START = "</p>";
    private static final String HTML_ADDRESS = "http";
    private static final String TAG_IMAGE = "<img src";

    private static final String ITEM = "item";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String LINK = "link";
    private static final String DATE = "pubDate";

    private static final String JPG = ".jpg";
    private static final String PNG = ".png";
    private static final String GIF = ".gif";

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    private final Stack<String> elementMap = new Stack<>();
    private final Feed feed = new Feed();
    private final List<Item> itemList = new ArrayList<>();
    private boolean isItem;
    private StringBuilder builder;
    private Item item;

    @Override
    public void startElement(final String uri, final String name, final String qName, final Attributes attribute) throws SAXException {
        elementMap.push(qName);

        if (qName.equals(ITEM)) {

            setIsItem(true);

            setItem(new Item());

        } else if (isItem) {

            if (qName.equals(DESCRIPTION)) {

                setBuilder(new StringBuilder());

            }
        }
    }

    @Override
    public void endElement(String uri, String name, String qName) throws SAXException {
        elementMap.pop();

        if (qName.equals(ITEM)) {

            setIsItem(false);

            itemList.add(item);

        } else if (isItem) {

            if (qName.equals(DESCRIPTION)) {

                final String text = builder.toString();

                item.setDescription(getDescription(text));

                item.setUrlImage(getPhoto(text));
            }
        }
    }

    @Override
    public void characters(final char[] charArray, final int start, final int length) throws SAXException {

        final String value = new String(charArray, start, length);

        if (isItem) {

            if (TITLE.equals(getCurrentElement())) {

                item.setTitle(value);

            } else if (DESCRIPTION.equals(getCurrentElement())) {

                builder.append(value);

            } else if (DATE.equals(getCurrentElement())) {

                try {

                    item.setDate(DATE_FORMAT.parse(value));

                } catch (final ParseException e) {

                    Log.e(TAG, e.getMessage());
                }

            } else if (LINK.equals(getCurrentElement())) {

                item.setLink(value);

            }

        } else {

            if (TITLE.equals(getCurrentElement())) {

                feed.setTitle(value);

            } else if (DESCRIPTION.equals(getCurrentElement())) {

                feed.setDescription(value);

            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();

        RealmList<Item> realmList = new RealmList<>();

        for (final Item item : itemList) {

            item.setFeed(feed);

            realmList.add(item);
        }

        feed.setItemList(realmList);
    }

    private void setIsItem(final boolean isItem) {
        this.isItem = isItem;
    }

    private void setBuilder(final StringBuilder builder) {
        this.builder = builder;
    }

    private String getCurrentElement() {
        return elementMap.peek();
    }

    private void setItem(final Item item) {
        this.item = item;
    }

    private String getDescription(final String text) {

        final String[] textArray = text.split(TAG_PARAGRAPHY_END);

        for (final String value : textArray) {

            if (value.contains(TAG_PARAGRAPHY_START)) {

                final int start = value.indexOf(CLOSE_TAG) + 1;

                final int end = value.indexOf(TAG_PARAGRAPHY_START);

                return value.substring(start, end).trim();
            }
        }

        return null;
    }

    private String getPhoto(final String text) {

        final String[] textArray = text.split(TAG_IMAGE);

        for (final String value : textArray) {

            final String extension = getExtension(value);

            if (extension == null) {

                Log.e(TAG, "");

            } else {

                final int start = value.indexOf(HTML_ADDRESS);

                final int end = value.indexOf(extension) + extension.length();

                return value.substring(start, end).trim();
            }
        }

        return null;
    }

    private String getExtension(final String path) {

        if (path.contains(JPG)) {

            return JPG;

        } else if (path.contains(PNG)) {

            return PNG;

        } else if (path.contains(GIF)) {
            
            return GIF;
        }

        return null;
    }

    public final Feed getFeed() {
        return feed;
    }
}
