package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Blue on 2016/5/24.
 */
public class UserDbHelper extends SQLiteOpenHelper {

    //建表语句
    public static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "username text, "
            + "tel text, "
            + "userpwd text)";

    public static final String CREATE_PRODUCT = "create table Product ("
            + "id integer primary key autoincrement, "
            + "productMac text)";

    public UserDbHelper(Context context, String name, CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_PRODUCT);//创建产品表
        db.execSQL(CREATE_USER);//创建用户表

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
