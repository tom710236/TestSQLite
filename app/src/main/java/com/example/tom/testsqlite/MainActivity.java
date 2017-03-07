package com.example.tom.testsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        *建立資料表
        *用SQL語法"CREATE TABLE IF NOT EXISTS "
        *不存在時才建立
        * */
        String createTable="CREATE TABLE IF NOT EXISTS "
                + tb_name + " ("
                + "name"  + " TEXT NOT NULL, "
                + "phone" + " INTEGER PRIMARY KEY,"
                + "email" + " TEXT"
                + ")";
        //呼叫execSQL 執行 createTable
        db.execSQL(createTable);
        //呼叫自訂的addData方法 在資料表新增兩筆紀錄
        addData("tom","0220019891","tom@yahoo.com.tw");
        addData("Mary","022934198","Mary@yahoo.com.tw");
        //顯示資料庫一些基本資料
        TextView txv=(TextView)findViewById(R.id.textView);
        txv.setText("資料庫路徑: "+db.getPath()+"\n"+
                    "資料庫分頁大小: "+db.getPageSize()+"Bytes\n"+
                    "資料量上限: "+db.getMaximumSize()+"Bytes\n");
        db.close();
    }
    /*
     *自訂的方法
     *把三個參數字串加到資料表中成為一筆紀錄
     *建立含3個項目的ContentValues
     *並用put()方法將其字串加到物件中
     *
     * */
    private void addData(String name,String phone,String email){
        ContentValues cv = new ContentValues(3);
        cv.put("naem",name);
        cv.put("phone",phone);
        cv.put("emial",email);
        //將資料加到資料表
        db.insert(tb_name,null,cv);
    }
}
