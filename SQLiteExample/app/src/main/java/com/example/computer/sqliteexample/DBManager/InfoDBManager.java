package com.example.computer.sqliteexample.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class InfoDBManager {
    // DB 명, 테이블 명, DB 버전 정보를 정의한다.
    // ========================================================================
    static final String DB_INFO = "Info.db";
    static final String TABLE_INFOS = "Infos";
    static final int DB_VERSION = 1;
    // ========================================================================

    Context mContext = null;

    public static InfoDBManager mDbManager = null;
    private SQLiteDatabase mDatabase = null;

    // DB 매니저 객체는 싱글톤(singleton)으로 구현하도록 한다.
    // ========================================================================
    public static InfoDBManager getInstance(Context context) {
        if (mDbManager == null) {
            mDbManager = new InfoDBManager(context);
        }

        return mDbManager;
    }
    // ========================================================================

    private InfoDBManager(Context context) {
        mContext = context;

        // 1. DB Manager를 생성할 때 DB 생성 및 열어 둔다. 
        // ====================================================================
        mDatabase = context.openOrCreateDatabase(DB_INFO,
                Context.MODE_PRIVATE,
                null);
        // ====================================================================

        // 2. 만일 DB에 테이블이 존재하지 않으면 생성한다. 
        // ====================================================================
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS  " + TABLE_INFOS +
                "(" + "_id  TEXT PRIMARY KEY, " +
                "pw        TEXT, " +
                "name     TEXT, " +
                "phone    TEXT); ");
        // ====================================================================
    }

    public long insert(ContentValues addRowValue) {
        // 아래와 같이 execSQL 함수를 이용해 직접 SQL 문으로
        // 레코드를 추가할 수도 있다.
        /* --------------------------------------------------------------------
        mDatabase.execSQL( "INSERT INTO " + TABLE_STUDENTS +  
                             " VALUES(" +
                                     "null, " +
                                     "'200106054'," +
                                     "'홍길동'," +
                                     "'컴퓨터'," +
                                     "3);" );
        -------------------------------------------------------------------- */

        return mDatabase.insert(TABLE_INFOS, null, addRowValue);
    }

    public Cursor query(String[] columns,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
                        String orderBy) {
        // 아래와 같이 rawQuery 함수를 이용해 직접 SELECT 문으로
        // 추가된 레코드 정보를 얻어올 수도 있다.
        /* --------------------------------------------------------------------
        Cursor   c = null;
        c = mDatabase.rawQuery( "SELECT * FROM " + TABLE_STUDENTS, null );
        c = mDatabase.rawQuery( "SELECT * FROM " + TABLE_STUDENTS, null );
        -------------------------------------------------------------------- */

        return mDatabase.query(TABLE_INFOS,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    public int update(ContentValues updateRowValue,
                      String whereClause,
                      String[] whereArgs) {
        // 아래와 같이 execSQL 함수를 이용해 직접 SQL 문으로
        // 레코드를 갱신할 수도 있다.
        /* --------------------------------------------------------------------
        mStudentsDB.execSQL( "UPDATE " + TABLE_STUDENTS +  
                             " SET name = '고길동'" +
                             " WHERE number = '200106054';" );
        -------------------------------------------------------------------- */

        return mDatabase.update(TABLE_INFOS,
                updateRowValue,
                whereClause,
                whereArgs);
    }

    public int delete(String whereClause,
                      String[] whereArgs) {
        // 아래와 같이 execSQL 함수를 이용해 직접 SQL 문으로
        // 레코드를 삭제할 수도 있다.
        /* --------------------------------------------------------------------
        mStudentsDB.execSQL( "DELETE FROM " + TABLE_STUDENTS );
        -------------------------------------------------------------------- */

        return mDatabase.delete(TABLE_INFOS, whereClause, whereArgs);
    }


}
