package com.humanrelationship.leeyun1533.humanrelationship.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import  com.humanrelationship.leeyun1533.humanrelationship.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.user_name)
    TextView name;

    @BindView(R.id.user_phone_num)
    TextView phoneNum;
    public ContactListViewHolder( View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
