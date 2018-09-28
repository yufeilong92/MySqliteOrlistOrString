# MySqliteOrlistOrString
*  加密解密工具
```
package com.example.mysqlite.Utils;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/27 13:33
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SerializableUtil {
    /**
     * @param list 要保存list数据
     * @param <E>
     * @return
     * @throws IOException
     */
    public static <E> String list2String(List<E> list) throws IOException {
        //实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        //writeObject 方法负责写入特定类的对象的状态，以便相应的readObject可以还原它
        oos.writeObject(list);
        //最后，用Base64.encode将字节文件转换成Base64编码，并以String形式保存
        String listString = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        //关闭oos
        oos.close();
        return listString;
    }

    /**
     * @param obj 要保存的任意类型的
     * @return
     * @throws IOException
     */
    public static String obj2Str(Object obj) throws IOException {
        if (obj == null) {
            return "";
        }
        //实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        //writeObject 方法负责写入特定类的对象的状态，以便相应的readObject可以还原它
        oos.writeObject(obj);
        //最后，用Base64.encode将字节文件转换成Base64编码，并以String形式保存
        String listString = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        //关闭oos
        oos.close();
        return listString;
    }


    /**
     * @param str 将保存的任意数据还原成Object
     * @return
     * @throws StreamCorruptedException
     * @throws IOException
     */ //将序列化的数据还原成Object
    public static Object str2Obj(String str) throws StreamCorruptedException, IOException {
        byte[] mByte = Base64.decode(str.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(mByte);
        ObjectInputStream ois;
        if (bais.available() != 0)
            ois = new ObjectInputStream(bais);
        else
            return null;
        try {
            return ois.readObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param str 将保存的序列化的list还原成list
     * @param <E>
     * @return
     * @throws StreamCorruptedException
     * @throws IOException
     */
    public static <E> List<E> string2List(String str) throws StreamCorruptedException, IOException {
        byte[] mByte = Base64.decode(str.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(mByte);
        ObjectInputStream ois = new ObjectInputStream(bais);
        List<E> stringList = null;
        try {
            stringList = (List<E>) ois.readObject();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stringList;
    }
}

```
*  SP保存工具类
```
package com.example.mysqlite.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 保存最后用户id
 * @author: L-BackPacker
 * @date: 2018/4/28 8:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SaveUUidUtil {
    // 用户名key
    public final static String KEY_NAME = "useruuid";
    private static SaveUUidUtil sharedUserUtils;
    private final SharedPreferences msp;
    private String s_User =null;

    @SuppressLint("WrongConstant")
    public SaveUUidUtil(Context context) {
        msp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE | Context.MODE_APPEND);
    }

    public static synchronized void initSharedPreference(Context context) {
        if (sharedUserUtils == null) {
            sharedUserUtils = new SaveUUidUtil(context);
        }
    }
    public static synchronized SaveUUidUtil getInstance() {
        return sharedUserUtils;
    }
    public SharedPreferences getSharedPref()
    {
        return msp;
    }
    public  synchronized void putUUID(String  vo){
        SharedPreferences.Editor editor = msp.edit();
        String str="";
        try {
            str = SerializableUtil.obj2Str(vo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.putString(KEY_NAME,str);
        editor.commit();
        s_User = vo;
    }
   public synchronized String  getUserId(){
       if (s_User == null)
       {
           s_User = new String();
           //获取序列化的数据
           String str = msp.getString(SaveUUidUtil.KEY_NAME, "");
           try {
               Object obj = SerializableUtil.str2Obj(str);
               if(obj != null){
                   s_User = (String) obj;
               }
           } catch (StreamCorruptedException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return s_User;
   }
   public  synchronized void delectUUid(){
       SharedPreferences.Editor editor = msp.edit();
       editor.putString(KEY_NAME,"");
       editor.commit();
       s_User = null;
   }
}
```
* 数据库保存
```
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
```
* 加密解密
```
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
```

![](https://github.com/yufeilong92/MySqliteOrlistOrString/icon/a.jpg)
![](https://github.com/yufeilong92/MySqliteOrlistOrString/icon/b.jpg)

*
[详情使用点击](https://www.cnblogs.com/RaphetS/p/5996265.html)

[官网使用](https://realm.io/docs/java/latest/)