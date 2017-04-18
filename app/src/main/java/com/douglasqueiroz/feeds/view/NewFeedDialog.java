package com.douglasqueiroz.feeds.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.douglasqueiroz.feeds.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by @douglas
 */

public class NewFeedDialog extends DialogFragment {

    public static final String TAG = NewFeedDialog.class.getSimpleName();

    private NewFeedDialogListner listner;

    @BindView(R.id.edt_link)
    EditText edtLink;

    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @BindView(R.id.btn_save)
    Button btnSavel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public void setListner(NewFeedDialogListner listner) {
        this.listner = listner;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_new_feed, container, false);
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (listner != null) {
            String url = edtLink.getText().toString().trim();
            listner.onClickSave(url);
        }
    }

    @OnClick(R.id.btn_cancel)
    public void onBtnCancelClicked() {
        if (listner != null) {
            listner.onClickCancel();
        }
    }


    public interface NewFeedDialogListner {
        void onClickSave(String url);
        void onClickCancel();
    }
}
