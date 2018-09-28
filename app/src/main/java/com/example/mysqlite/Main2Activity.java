package com.example.mysqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mysqlite.Utils.SerializableUtil;
import com.example.mysqlite.Vo.Dog;
import com.example.mysqlite.Vo.Person;
import com.example.mysqlite.Vo.SelectVideoFGVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnAddObject;
    private Button mBtnAddArraylist;
    private Button mBtnFind;
    private Button mBtnFindArraylist;
    private TextView mTvData;
    private Button mBtnDelectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        mBtnAddObject = (Button) findViewById(R.id.btn_add_object);
        mBtnAddArraylist = (Button) findViewById(R.id.btn_add_Arraylist);
        mBtnFind = (Button) findViewById(R.id.btn_find);
        mBtnFindArraylist = (Button) findViewById(R.id.btn_find_Arraylist);
        mTvData = (TextView) findViewById(R.id.tv_data);

        mBtnAddObject.setOnClickListener(this);
        mBtnAddArraylist.setOnClickListener(this);
        mBtnFind.setOnClickListener(this);
        mBtnFindArraylist.setOnClickListener(this);
        mBtnDelectAll = (Button) findViewById(R.id.btn_delectAll);
        mBtnDelectAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_object:
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Dog dog = realm.createObject(Dog.class);

                SelectVideoFGVo vo = new SelectVideoFGVo();
                vo.setName("测试名字");
                vo.setId("id");
                vo.setData("测试内容时多少");
                try {
                    String str = SerializableUtil.obj2Str(vo);
                    dog.setAge(2);
                    dog.setName(str);
                    realm.commitTransaction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_add_Arraylist:
                Realm realm1 = Realm.getDefaultInstance();
                realm1.beginTransaction();
                Person person = realm1.createObject(Person.class);
                int a = 10;
                ArrayList<SelectVideoFGVo> vos = new ArrayList<>();
                for (int i = 0; i < a; i++) {
                    SelectVideoFGVo vo1 = new SelectVideoFGVo();
                    vo1.setName("测试名字");
                    vo1.setId("id");
                    vo1.setData("测试内容时多少");
                    vos.add(vo1);
                }
                try {
                    String str = SerializableUtil.list2String(vos);
                    person.setName(str);
                    person.setTitle("测试标题");
                    realm1.commitTransaction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_find:
                Realm realm2 = Realm.getDefaultInstance();
                RealmResults<Dog> dogs = realm2.where(Dog.class).findAll();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < dogs.size(); i++) {
                    Dog dog2 = dogs.get(i);
                    if (!TextUtils.isEmpty(dog2.getName())) {
                        buffer.append("加密后的：" + dog2.getName() + dog2.getAge() + "\n");
                        try {
                            SelectVideoFGVo vo1 = new SelectVideoFGVo();
                            Object obj = SerializableUtil.str2Obj(dog2.getName());
                            if (obj != null) {
                                vo1 = (SelectVideoFGVo) obj;
                                buffer.append("解密后的：" + vo1.getData() + vo1.getId() + vo1.getName() + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                mTvData.setText(buffer.toString());
                break;
            case R.id.btn_find_Arraylist:
                Realm realm4 = Realm.getDefaultInstance();

                RealmResults<Person> peoples = realm4.where(Person.class).findAll();
                StringBuffer buffer1 = new StringBuffer();
                for (int i = 0; i < peoples.size(); i++) {
                    Person person1 = peoples.get(i);
                    if (!TextUtils.isEmpty(person1.getName())) {
                        buffer1.append("加密后：" + person1.getName() + person1.getTitle() + "\n");
                        try {
                            List<SelectVideoFGVo> lists = SerializableUtil.<SelectVideoFGVo>string2List(person1.getName());
                            for (int k = 0; k < lists.size(); k++) {
                                SelectVideoFGVo videoFGVo = lists.get(k);
                                buffer1.append("解密后：" + videoFGVo.getData() + videoFGVo.getName() + videoFGVo.getId());
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                mTvData.setText(buffer1.toString());
                break;
            case R.id.btn_delectAll:
                final Realm realm3 = Realm.getDefaultInstance();
                final RealmResults<Dog> dogs1 = realm3.where(Dog.class).findAll();
                final RealmResults<Person> people = realm3.where(Person.class).findAll();
                realm3.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        dogs1.deleteAllFromRealm();
                        people.deleteAllFromRealm();
                    }
                });
                break;
        }
    }
}
