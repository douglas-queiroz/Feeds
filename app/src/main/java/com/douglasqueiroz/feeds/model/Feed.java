package com.douglasqueiroz.feeds.model;

import org.parceler.Parcel;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by @douglas
 */

public class Feed extends RealmObject {

    private String title;
    private String link;
    private String description;
    private RealmList<Item> itemList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(RealmList<Item> itemList) {
        this.itemList = itemList;
    }
}
