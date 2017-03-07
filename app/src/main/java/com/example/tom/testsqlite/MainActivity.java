package com.example.tom.testsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


/*
*
*
* */
public class MainActivity extends AppCompatActivity {
    //資料庫名稱
    static final String db_name="testDB2";
    //資料表名稱
    static final String tb_name="test2";
    //SQLiteDatabase類別內建許多用來執行SQL的方法
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //開啟或建立資料庫 (參數1 資料庫名稱 參數2 內建參數(此資料庫要給那些人使用) 參數3 ErrorHandler)
        db=openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
       /*
        *建立資料表內容
        *用SQL語法"CREATE TABLE IF NOT EXISTS "
        *不存在時才建立
        * */
        String createTable="CREATE TABLE IF NOT EXISTS "
                + tb_name + " ("
                + "name"  + " TEXT NOT NULL, "
                + "phone" + " INTEGER PRIMARY KEY,"
                + "email" + " TEXT"
                + ")";
        //呼叫execSQL 執行 createTable 建立資料表
        db.execSQL(createTable);
        //呼叫rawQuery()執行SELECT查詢敘述 並將結果設定給Cursor物件c
        Cursor c =db.rawQuery("SELECT * FROM " +tb_name,null);
        /*
        *檢查查詢結果資料筆數是否為0 若是為0(沒有資料)就執行addData新增資料
        *新增後再用db.rawQuery重新執行查詢
        **/
        if (c.getCount()==0){
            addData("tom","0220019891","tom@yahoo.com.tw");
            addData("Mary","022934198","Mary@yahoo.com.tw");
            c =db.rawQuery("SELECT * FROM " +tb_name,null);
        }
        /*
        * 再有資料的情況下
        * 用moveFirst逐步檢查
        * 並顯示在TextView
        * */

        if(c.moveToFirst()){
            String str = "總共有 "+c.getCount()+"筆資料\n";
            str+="-----\n";
            Log.e("str",str);
            Log.e("c.getCount", String.valueOf(c.getCount()));

            do{
                        str+="name:"+c.getString(0)+"\n";
                        str+="phone:"+c.getString(1)+"\n";
                        str+="email"+c.getString(2)+"\n";
                        Log.e("str2",str);

                    str+="-----\n";

            //有下一筆就繼續查詢(moveToNext)
            }while (c.moveToNext());

            TextView textView = (TextView)findViewById(R.id.textView);
            textView.setText(str);
        }
        db.close();

    }
    private void addData(String name,String phone,String email){
        ContentValues cv = new ContentValues(3);
        cv.put("name",name);
        cv.put("phone",phone);
        cv.put("email",email);
        Log.e("CV", String.valueOf(cv));
        //將資料加到資料表
        db.insert(tb_name,null,cv);
    }
}
