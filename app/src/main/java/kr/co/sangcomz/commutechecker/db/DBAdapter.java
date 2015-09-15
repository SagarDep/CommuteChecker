package kr.co.sangcomz.commutechecker.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import kr.co.sangcomz.commutechecker.bean.CommuteTimeBean;

/**
 * DB 관리
 */

public class DBAdapter extends SQLiteOpenHelper {
    private static final String DB_NAME = "commute.db"; //대문자로...쓰지마
    private static final int VERSION = 1;
    private static final String ID = "_id";
    private static final String COMMUTE_TIME = "commute_time";


   private static final String TABLE_NAME = "commute_time";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " integer primary key autoincrement, " +
                    COMMUTE_TIME + " integer not null )";




    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        super(context, DB_NAME, null, VERSION);
        db = this.getWritableDatabase();
    }



    @Override
    public synchronized void close() {
        db.close();
        super.close();
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);//객관식 생성 테이블
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public boolean insertCommuteTime(int time) {
        ContentValues cv = new ContentValues();
        cv.put(COMMUTE_TIME, time);
        return db.insert(TABLE_NAME, null, cv) != -1;
    }


    public ArrayList<CommuteTimeBean> getCommuteTime() {
        ArrayList<CommuteTimeBean> commuteTimeBeans = new ArrayList<>();
        //Cursor c = db.query(TABLE_NAME, new String[] {ID, TIME, DATE, PERIOD, QUESTIONNUMBER, EXAMPLE, QUESTION }, null, null, null, null, null, null, null, ID + " DESC");
        Cursor c=db.rawQuery("select * from commute_time", null);
        if (c.moveToFirst()) {
            final int indexId = c.getColumnIndex(ID);
            final int indexCommuteTime = c.getColumnIndex(COMMUTE_TIME);


            do {
                int id = c.getInt(indexId);
                int commuteTime = c.getInt(indexCommuteTime);



                commuteTimeBeans.add(new CommuteTimeBean(id, commuteTime));
            } while (c.moveToNext());
        }
        c.close();

        return commuteTimeBeans;
    }

    public boolean updateCommuteTime(CommuteTimeBean commuteTimeBean) {
        ContentValues cv = new ContentValues();
        cv.put(COMMUTE_TIME, commuteTimeBean.getCommuteTime());
        String[] params = new String[] { Integer.toString(commuteTimeBean.getId()) };
        int result = db.update(TABLE_NAME, cv, ID + "=?", params);
        return result > 0;
    }

    public boolean deleteCommuteTime(int id) {
        String[] params = new String[] { Integer.toString(id) };
        int result = db.delete(TABLE_NAME, ID + "=?", params);
        return result > 0;
    }
}
