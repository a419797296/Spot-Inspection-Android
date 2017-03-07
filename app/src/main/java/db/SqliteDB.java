package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.UserInfo;


/**
 * Created by Blue on 2016/5/25.
 */
public class SqliteDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "sqlite_dbname";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static SqliteDB sqliteDB;

    private SQLiteDatabase db;

    public SqliteDB(Context context) {
        UserDbHelper dbHelper = new UserDbHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取SqliteDB实例
     * @param context
     */
    public synchronized static SqliteDB getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new SqliteDB(context);
        }
        return sqliteDB;
    }

    /**
     * 将User实例存储到数据库。
     */
    public int  saveUser(UserInfo user) {
        if (user != null) {
           /* ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("userpwd", user.getUserpwd());
            db.insert("User", null, values);*/

            String  username=user.getUserName();
            // String  tel=user.getTel();
            String  userpwd=user.getUserPassword();
            Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{username});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
//                    db.execSQL("insert into User(username,tel,userpwd) values(?,?,?) ", new String[]{username, tel,userpwd});
                    db.execSQL("insert into User(username,userpwd) values(?,?) ", new String[]{username,userpwd});
                    cursor.close();
                } catch (Exception e) {
                    Log.d("错误", e.getMessage());
                }
                return 1;
            }
        }
        else {
            return 0;
        }
    }

    /**
     * 从数据库读取User信息。
     */
    public List<UserInfo> loadUser() {
        List<UserInfo> list = new ArrayList<UserInfo>();
        Cursor cursor = db
                .query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                UserInfo user = new UserInfo();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setUserName(cursor.getString(cursor
                        .getColumnIndex("username")));
                user.setUserPassword(cursor.getString(cursor
                        .getColumnIndex("userpwd")));
                list.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public int Quer(String pwd,String name)
    {


        HashMap<String,String> hashmap=new HashMap<String,String>();
        Cursor cursor =db.rawQuery("select * from User where username=?", new String[]{name});

        // hashmap.put("name",db.rawQuery("select * from User where name=?",new String[]{name}).toString());
        if (cursor.getCount()>0)
        {
            Cursor pwdcursor =db.rawQuery("select * from User where userpwd=? and username=?",new String[]{pwd,name});
            cursor.close();
            if (pwdcursor.getCount()>0)
            {
                return 1;
            }
            else {
                return -1;
            }
        }
        else {
            return 0;
        }


    }

//
//    ///---------------------------------------------------------------
//    /**
//     * 将Product实例存储到数据库。
//     */
//    public int  saveProduct(ConfigProduct product) {
//        if (product != null) {
//
//            String  productMac=ConfigProduct.getProductMac();
//            Cursor cursor = db.rawQuery("select * from Product where productMac=?", new String[]{productMac});
//            if (cursor.getCount() > 0) {
//                return -1;
//            } else {
//                try {
//                    db.execSQL("insert into Product(productMac) values(?) ", new String[]{productMac});
//                    cursor.close();
//                } catch (Exception e) {
//                    Log.d("错误", e.getMessage());
//                }
//                return 1;
//            }
//        }
//        else {
//            return 0;
//        }
//    }
//
//    /**
//     * 从数据库读取Product信息。
//     */
//    public List<ConfigProduct> loadProduct() {
//        List<ConfigProduct> list = new ArrayList<ConfigProduct>();
//        Cursor cursor = db
//                .query("Product", null, null, null, null, null, null);
//        if (cursor.moveToFirst()) {
//            do {
//                ConfigProduct product = new ConfigProduct();
//                ConfigProduct.setProductMac(cursor.getString(cursor
//                        .getColumnIndex("productMac")));
//                list.add(product);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return list;
//    }


}
