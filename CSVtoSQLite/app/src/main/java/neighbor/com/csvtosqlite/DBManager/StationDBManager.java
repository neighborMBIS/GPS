package neighbor.com.csvtosqlite.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 2016-08-29.
 */
public class StationDBManager {
    private static StationDBManager ourInstance = null;

    public static StationDBManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new StationDBManager(context);
        }
        return ourInstance;
    }

    Context ctx;

    private SQLiteDatabase mDatabase = null;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MBISs.db";
    private static final String TABLE_NAME = "Station";

    private static final String station_id = "station_id";
    private static final String station_nm = "station_nm";
    private static final String admin_nm = "admin_nm";
    private static final String sido_cd = "sido_cd";
    private static final String x = "x";
    private static final String y = "y";

    private StationDBManager(Context context) {
        ctx = context;
        mDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        String makeDB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_id integer primary key autoincrement," +
                station_id + " TEXT," +
                station_nm + " TEXT," +
                admin_nm + " TEXT," +
                sido_cd + " TEXT," +
                x + " TEXT," +
                y + " TEXT " +
                ");";

        mDatabase.execSQL(makeDB);
    }

    public long insert(ContentValues addRowValue) {
        return mDatabase.insert(TABLE_NAME, null, addRowValue);
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

        return mDatabase.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    public int delete(String whereClause,
                           String[] whereArgs) {
        // 아래와 같이 execSQL 함수를 이용해 직접 SQL 문으로
        // 레코드를 삭제할 수도 있다.
        /* --------------------------------------------------------------------
        mStudentsDB.execSQL( "DELETE FROM " + TABLE_STUDENTS );
        -------------------------------------------------------------------- */

        return mDatabase.delete(TABLE_NAME, whereClause, whereArgs);
    }


}
