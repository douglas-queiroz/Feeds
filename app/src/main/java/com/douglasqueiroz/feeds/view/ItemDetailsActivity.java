package com.douglasqueiroz.feeds.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.douglasqueiroz.feeds.R;
import com.douglasqueiroz.feeds.model.Item;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailsActivity extends AppCompatActivity {

    public static final String ITEM = "item";


    @BindView(R.id.iv_image)
    SimpleDraweeView ivImage;

    @BindView(R.id.tx_title)
    TextView txTitle;

    @BindView(R.id.tx_description)
    TextView txDescription;

    Item item;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.item = getIntent().getExtras().getParcelable(ITEM);

        setContentView(R.layout.activity_item_details);

        ButterKnife.bind(this);

        ivImage.setImageURI(item.getUrlImage());

        txTitle.setText(item.getTitle());

        txDescription.setText(Html.fromHtml(item.getDescription()));
    }
}
