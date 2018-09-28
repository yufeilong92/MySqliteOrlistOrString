package com.example.mysqlite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mysqlite.Utils.SerializableUtil;
import com.example.mysqlite.Vo.Dog;
import com.example.mysqlite.Vo.SelectVideoFGVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                final Dog dogs = realm.createObject(Dog.class);
                int a = 10;
                ArrayList<SelectVideoFGVo> vos = new ArrayList<>();
                for (int i = 0; i < a; i++) {
                    SelectVideoFGVo vo = new SelectVideoFGVo();
                    vo.setData("你好");
                    vo.setId("21");
                    vo.setName("小红");
                    vos.add(vo);
                }
                try {
                    String s = SerializableUtil.obj2Str(vos);
                    dogs.setName(s);
                    dogs.setAge(1);
                    realm.commitTransaction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

                    String name = dog.getName();
                    List<SelectVideoFGVo> fgVo = new ArrayList<SelectVideoFGVo>();
                    try {
                        List<SelectVideoFGVo> list = SerializableUtil.string2List(name);
                        if (list != null) {
                            for (int l = 0; l < list.size(); l++) {
                                SelectVideoFGVo vo = list.get(l);
                                buffer.append("解析数据："+vo.getName()+vo.getId()+vo.getData());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                mTvData.setText(buffer.toString());
                break;
        }
    }
}
