package com.humanrelationship.leeyun1533.humanrelationship;

import android.Manifest;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.databinding.DataBindingUtil;



import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.humanrelationship.leeyun1533.humanrelationship.Model.ContactData;
import com.humanrelationship.leeyun1533.humanrelationship.RecyclerView.ContactListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> dataList;

    RecyclerView contactRecylerView;

    RecyclerView.LayoutManager layoutManager;
    ContactListAdapter contactListAdapter;

    @BindView(R.id.load_contact_list_button)
    Button load_contact_list_button;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        contactRecylerView = (RecyclerView) findViewById(R.id.contact_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        contactRecylerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        contactListAdapter = new ContactListAdapter();
        contactRecylerView.setAdapter(contactListAdapter);
        CheckingSearchTextChange();
        Log.e("dd","dd");
    }

    @OnClick(R.id.load_contact_list_button)
    public void loadContact() {
        Log.e("load","contact");
        PermissionCheck();
    }

    public void PermissionCheck() {
        PermissionListener permissionListener= new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.e("granted","granted");
                getContact();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this,"권한거부\n"+ deniedPermissions.toString(),Toast.LENGTH_SHORT).show();
            }

        };
        TedPermission.with(getBaseContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("구글 로그인을 하기 위해서는 주소록 접근 권한이 필요해요")
                .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();
    }
    public void getContact() {
        dataList = new ArrayList<HashMap<String, String>>();
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");
        ArrayList<ContactData> contactDataArrayList = new ArrayList<>();


        while (c.moveToNext()) {
            // 연락처 id 값
            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            // 연락처 대표 이름
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            ContactData contactData = new ContactData();
            contactData.setName(name);
            Log.e("name",name);

            // ID로 전화 정보 조회
            Cursor phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null, null);

            // 데이터가 있는 경우
            if (phoneCursor.moveToFirst()) {
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactData.setPhoneNumber(number);
            }
            contactDataArrayList.add(contactData);

            phoneCursor.close();
            Log.e("contactlist",contactDataArrayList.get(0).getName());
        }// end while
        contactListAdapter.setContactListData(contactDataArrayList);

        c.close();

//        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
//                dataList,
//                android.R.layout.simple_list_item_2,
//                new String[]{"name", "phone"},
//                new int[]{android.R.id.text1, android.R.id.text2});
//        mListview.setAdapter(adapter);
    }

    public void CheckingSearchTextChange() {
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("edittext값", s.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,

                                          int after) {
            }


            @Override
            public void afterTextChanged(Editable s) {

                String text = searchEditText.getText().toString()

                        .toLowerCase(Locale.getDefault());
                Log.e("edittext값.",text);
                contactListAdapter.filter(text);


            }
        });

    }


}
