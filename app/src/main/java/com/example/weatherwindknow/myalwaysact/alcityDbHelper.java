package com.example.weatherwindknow.myalwaysact;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;

import java.util.ArrayList;
import java.util.List;

public class alcityDbHelper extends SQLiteOpenHelper {
    private static alcityDbHelper sHelper;
    private static final String DB_NAME = "alcity.db";   //数据库名
    private static final int VERSION = 1;    //版本号
    private Gson gson = new Gson();
    String madcode;

    //必须实现其中一个构方法
    public alcityDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建单例，供使用调用该类里面的的增删改查的方法
    public synchronized static alcityDbHelper getInstance(Context context) {
        if (null == sHelper) {
            sHelper = new alcityDbHelper(context, DB_NAME, null, VERSION);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建user_table表
        db.execSQL("create table alcity_table(_id integer primary key autoincrement, " +
                "adcode text," +
                "location text," +
                "map text," +
                "mweatherHourlyBean text," +
                "mWeatherDailyBean text" +
                ")");

    }

    //    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alcity_table");
        onCreate(db);
    }

    public int add(String adcode, String location, String map,WeatherHourlyBean mweatherHourlyBean, WeatherDailyBean mWeatherDailyBean) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //填充占位符
        values.put("adcode", adcode);
        values.put("location", location);
        values.put("map", map);
        values.put("mweatherHourlyBean", gson.toJson(mweatherHourlyBean));
        values.put("mWeatherDailyBean", gson.toJson(mWeatherDailyBean));
//        String nullColumnHack = "values(null,?,?)";
        String nullColumnHack = "values(null,?,?,?,?,?)";
        //执行
        int insert = (int) db.insert("alcity_table", nullColumnHack, values);
        db.close();
        return insert;
    }

    @SuppressLint("Range")
    public List<alcity> queryRegisterListData() {
        SQLiteDatabase db = getReadableDatabase();
        List<alcity> list = new ArrayList<>();
        String sql = "select _id, adcode, location, map, mweatherHourlyBean, mWeatherDailyBean from alcity_table";
        // 修正查询的表名和列名
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String adcode = cursor.getString(cursor.getColumnIndex("adcode"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String map = cursor.getString(cursor.getColumnIndex("map"));
            String mweatherHourlyBean = cursor.getString(cursor.getColumnIndex("mweatherHourlyBean"));
            WeatherHourlyBean mweatherHourlyBean1 = gson.fromJson(mweatherHourlyBean, WeatherHourlyBean.class);
            String mWeatherDailyBean = cursor.getString(cursor.getColumnIndex("mWeatherDailyBean"));
            WeatherDailyBean mWeatherDailyBean1 = gson.fromJson(mWeatherDailyBean, WeatherDailyBean.class);
            alcity alcity = new alcity(adcode, location, map, mweatherHourlyBean1, mWeatherDailyBean1);
            list.add(alcity);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("alcity_table", "adcode != ?", new String[]{madcode});
        db.close();
    }

    public void deleteByAdcode(String adcode) {
        SQLiteDatabase db = getWritableDatabase();
        // 使用参数化查询来防止SQL注入
        String whereClause = "adcode=?";
        String[] whereArgs = new String[]{adcode};

        // 执行删除操作
        db.delete("alcity_table", whereClause, whereArgs);
        db.close();
    }

    public void updateByAdcode(String adcode, String location, String map,WeatherHourlyBean mweatherHourlyBean, WeatherDailyBean mWeatherDailyBean) {
        SQLiteDatabase db = getWritableDatabase();
        // 更新条件
        String whereClause = "adcode = ?";
        String[] whereArgs = new String[]{adcode};
        // 要更新的值
        ContentValues values = new ContentValues();
        values.put("adcode", adcode);
        values.put("location", location);
        values.put("map", map);
        values.put("mweatherHourlyBean", gson.toJson(mweatherHourlyBean));
        values.put("mWeatherDailyBean", gson.toJson(mWeatherDailyBean));
        // 执行更新
        int affectedRows = db.update("alcity_table", values, whereClause, whereArgs);
        // 检查是否有记录被更新
        if (affectedRows > 0) {
            Log.d("Update", "更新 " + affectedRows + " 行.");
        } else {
            Log.d("Update", "没找到这个地方的记录: " + adcode);
            add(adcode, location, map,mweatherHourlyBean, mWeatherDailyBean);
        }
        // 关闭数据库
        db.close();
    }
    public synchronized void updateByAlCity(alcity alcity) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String whereClause = "adcode = ?";
            String[] whereArgs = new String[]{alcity.adcode};
            ContentValues values = new ContentValues();
            values.put("adcode", alcity.adcode);
            values.put("location", alcity.location);
            values.put("map", alcity.map);
            values.put("mweatherHourlyBean", gson.toJson(alcity.mweatherHourlyBean));
            values.put("mWeatherDailyBean", gson.toJson(alcity.mWeatherDailyBean));

            int affectedRows = db.update("alcity_table", values, whereClause, whereArgs);
            if (affectedRows == 0) {
                add(alcity.adcode, alcity.location, alcity.map, alcity.mweatherHourlyBean, alcity.mWeatherDailyBean);
            }
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

}