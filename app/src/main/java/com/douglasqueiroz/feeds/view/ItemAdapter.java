package com.douglasqueiroz.feeds.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.douglasqueiroz.feeds.R;
import com.douglasqueiroz.feeds.model.Item;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by @douglas
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd yyyy 'at' HH:mm", Locale.getDefault());

    public ItemAdapter(final Context context, final List<Item> itemList) {
        super(context, 0, itemList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_adpter, null);
        }

        SimpleDraweeView ivPhoto = (SimpleDraweeView) view.findViewById(R.id.iv_photo);
        TextView txTitle = (TextView) view.findViewById(R.id.tx_title);
        TextView txData = (TextView) view.findViewById(R.id.tx_date);

        Item item = getItem(position);
        if (item.getTitle() != null) {
            txTitle.setText(item.getTitle());
        }

        if (item.getDate() != null) {
            txData.setText(DATE_FORMAT.format(item.getDate()));
        }

        if (item.getUrlImage() != null) {
            ivPhoto.setImageURI(item.getUrlImage());
        }

        return view;
    }


}
