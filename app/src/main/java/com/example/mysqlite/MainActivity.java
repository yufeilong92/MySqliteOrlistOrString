package com.example.mysqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mysqlite.Vo.Dog;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "【" + MainActivity.class + "】==";
    private Button mBtnAdd;
    private Button mBtnDelect;
    private Button mBtnUpdata;
    private Button mBtnDelectOne;
    private Button mBtnFind;
    private TextView mTvData;
    private Button mBtnStartIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnDelect = (Button) findViewById(R.id.btn_delect);
        mBtnUpdata = (Button) findViewById(R.id.btn_updata);
        mBtnDelectOne = (Button) findViewById(R.id.btn_delect_one);

        mBtnAdd.setOnClickListener(this);
        mBtnDelect.setOnClickListener(this);
        mBtnUpdata.setOnClickListener(this);
        mBtnDelectOne.setOnClickListener(this);
        mBtnFind = (Button) findViewById(R.id.btn_find);
        mBtnFind.setOnClickListener(this);
        mTvData = (TextView) findViewById(R.id.tv_data);
        mBtnStartIntent = (Button) findViewById(R.id.btn_startIntent);
        mBtnStartIntent.setOnClickListener(this);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                 Dog dogs = realm.createObject(Dog.class);
                dogs.setName("小明");
                dogs.setAge(1);
                realm.commitTransaction();
                break;
            case R.id.btn_delect:
                Realm realm1 = Realm.getDefaultInstance();
                final RealmResults<Dog> dogs1 = realm1.where(Dog.class).findAll();
                realm1.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        dogs1.deleteAllFromRealm();
                    }
                });

                break;
            case R.id.btn_updata:
                Realm realm2 = Realm.getDefaultInstance();
                Dog first = realm2.where(Dog.class).equalTo("name", "小明").findFirst();
                if (first == null) return;
                realm2.beginTransaction();
                first.setName("小火");
                first.setAge(2);
                realm2.commitTransaction();

                break;
            case R.id.btn_delect_one:
                Realm realm4 = Realm.getDefaultInstance();
                final RealmResults<Dog> dogs2 = realm4.where(Dog.class).findAll();
                realm4.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (int i = 0; i < dogs2.size(); i++) {
                            Dog dog = dogs2.get(i);
                            dog.deleteFromRealm();
                        }
                    }
                });
                break;
            case R.id.btn_find:
                Realm realm3 = Realm.getDefaultInstance();
                RealmResults<Dog> all = realm3.where(Dog.class).findAll();
                StringBuffer buffer = new StringBuffer();

                for (int i = 0; i < all.size(); i++) {
                    Dog dog = all.get(i);
                    buffer.append(dog.getName() + "\n");
                    buffer.append(dog.getAge() + "\n");
                }
                mTvData.setText(buffer.toString());
                break;
            case R.id.btn_startIntent:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                break;
        }
    }


}
