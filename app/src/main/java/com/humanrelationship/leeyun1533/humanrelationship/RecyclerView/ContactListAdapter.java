package com.humanrelationship.leeyun1533.humanrelationship.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humanrelationship.leeyun1533.humanrelationship.Model.ContactData;
import com.humanrelationship.leeyun1533.humanrelationship.R;

import java.util.ArrayList;
import java.util.Locale;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListViewHolder> {
    Context context;
    ArrayList<ContactData> contactDataList = new ArrayList<>();
    ArrayList<ContactData> filteredContactDataList = new ArrayList<>();

    public void setContactListData(ArrayList<ContactData> contactListData) {
        contactDataList.addAll(contactListData);
        Log.e("inAdapter",contactListData.get(1).getName());
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_contact_list, parent, false);
        context= parent.getContext();
        return new ContactListViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(ContactListViewHolder viewHolder, int i) {
        Log.e("naananame",contactDataList.get(i).getName());
        if(!filteredContactDataList.isEmpty()) {
            viewHolder.name.setText(filteredContactDataList.get(i).getName());
            viewHolder.phoneNum.setText(filteredContactDataList.get(i).getPhoneNumber());

        }

    }

    @Override
    public int getItemCount() {
        return filteredContactDataList.size();
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            filteredContactDataList.addAll(contactDataList);
            Log.e("before filtered","엥");

        } else {
            filteredContactDataList.clear();
            for (ContactData contactData : contactDataList) {
                String name = contactData.getName();
                Log.e("before filtered",name);
                if (name.toLowerCase().contains(charText)) {
                    filteredContactDataList.add(contactData);
                    Log.e("filtered",filteredContactDataList.get(0).getName());
                }
            }
        }
        notifyDataSetChanged();
    }

}
