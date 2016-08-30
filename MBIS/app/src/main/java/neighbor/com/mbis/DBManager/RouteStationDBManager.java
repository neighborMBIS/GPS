package neighbor.com.mbis.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 2016-08-29.
 */
public class RouteStationDBManager {
    private static RouteStationDBManager ourInstance = null;

    public static RouteStationDBManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new RouteStationDBManager(context);
        }
        return ourInstance;
    }

    Context ctx;

    private SQLiteDatabase mDatabase = null;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MBISrs.db";
    private static final String TABLE_NAME = "RouteStation";

    private static final String route_id = "route_id";
    private static final String station_order = "station_order";
    private static final String station_id = "station_id";
    private static final String link_order = "link_order";
    private static final String remark = "remark";

    private RouteStationDBManager(Context context) {
        ctx = context;
        mDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        String makeDB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_id integer primary key autoincrement," +
                route_id + " TEXT," +
                station_order + " TEXT," +
                station_id + " TEXT," +
                link_order + " TEXT," +
                remark + " TEXT" +
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

    public Cursor myQuery(String key) {
        Cursor   c = null;
        c = mDatabase.rawQuery( "SELECT "+station_id+" FROM " + TABLE_NAME + " where : " + route_id + " = '" + key + "'", null );
        return c;
    }
}
