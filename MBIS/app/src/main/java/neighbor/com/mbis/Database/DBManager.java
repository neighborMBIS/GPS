package neighbor.com.mbis.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 2016-08-29.
 */
public class DBManager {
    private static DBManager ourInstance = null;

    public static DBManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DBManager(context);
        }
        return ourInstance;
    }

    Context ctx;

    private SQLiteDatabase mDatabase = null;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MBIS.db";
    private static final String TABLE_NAMER = "Route";
    private static final String TABLE_NAMES = "Station";
    private static final String TABLE_NAMERS = "RouteStation";
    private static final String TABLE_NAMERC = "Config";
    private static final String TABLE_NAMERBUF = "RouteBuf";
    private static final String TABLE_NAMESBUF = "StationBuf";
    private static final String TABLE_NAMERSBUF = "RouteStationBuf";

    private static final String id = "id";
    private static final String route_id = "route_id";
    private static final String st_sta_id = "st_sta_id";
    private static final String ed_sta_id = "ed_sta_id";
    private static final String company_nm = "company_nm";
    private static final String admin_nm = "admin_nm";
    private static final String company_id = "company_id";
    private static final String direction = "direction";


    private static final String station_id = "station_id";
    private static final String station_nm = "station_nm";
    private static final String sido_cd = "sido_cd";
    private static final String x = "x";
    private static final String y = "y";
    private static final String station_division = "station_division";


    private static final String station_order = "station_order";
    private static final String link_order = "link_order";
    private static final String remark = "remark";

    private static final String apply_date = "apply_date";
    private static final String apply_time = "apply_time";
    private static final String apply_file_name = "apply_file_name";
    private static final String apply_table_type = "apply_table_type";
    private static final String updatable = "updatable";



    private DBManager(Context context) {
        ctx = context;
        mDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        String makeDBConfig = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMERC + " (" +
                "_id integer primary key autoincrement," +
                apply_date + " TEXT," +
                apply_time + " TEXT," +
                apply_file_name + " TEXT, " +
                apply_table_type + " integer, " +
                updatable + " integer " +
                ");";

        String makeDBR = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMER + " (" +
                "_id integer primary key autoincrement," +
                id + " TEXT," +
                route_id + " TEXT," +
                st_sta_id + " TEXT," +
                ed_sta_id + " TEXT," +
                company_nm + " TEXT," +
                admin_nm + " TEXT," +
                company_id + " TEXT, " +
                direction + " TEXT " +
                ");";

        String makeDBS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMES + " (" +
                "_id integer primary key autoincrement," +
                station_id + " TEXT," +
                station_nm + " TEXT," +
                admin_nm + " TEXT," +
                sido_cd + " TEXT," +
                x + " TEXT," +
                y + " TEXT," +
                station_division + " TEXT " +
                ");";

        String makeDBRS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMERS + " (" +
                "_id integer primary key autoincrement," +
                route_id + " TEXT," +
                station_order + " integer," +
                station_id + " TEXT," +
                link_order + " TEXT," +
                direction + " TEXT, " +
                remark + " TEXT " +
                ");";


//        String makeDBRBuf = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMERBUF + " (" +
//                "_id integer primary key autoincrement," +
//                id + " TEXT," +
//                route_id + " TEXT," +
//                st_sta_id + " TEXT," +
//                ed_sta_id + " TEXT," +
//                company_nm + " TEXT," +
//                admin_nm + " TEXT," +
//                company_id + " TEXT, " +
//                direction + " TEXT " +
//                ");";
//
//        String makeDBSBuf = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMESBUF + " (" +
//                "_id integer primary key autoincrement," +
//                station_id + " TEXT," +
//                station_nm + " TEXT," +
//                admin_nm + " TEXT," +
//                sido_cd + " TEXT," +
//                x + " TEXT," +
//                y + " TEXT," +
//                station_division + " TEXT " +
//                ");";
//
//        String makeDBRSBuf = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMERSBUF + " (" +
//                "_id integer primary key autoincrement," +
//                route_id + " TEXT," +
//                station_order + " integer," +
//                station_id + " TEXT," +
//                link_order + " TEXT," +
//                direction + " TEXT, " +
//                remark + " TEXT " +
//                ");";


        mDatabase.execSQL(makeDBConfig);
        mDatabase.execSQL(makeDBR);
        mDatabase.execSQL(makeDBS);
        mDatabase.execSQL(makeDBRS);
//        mDatabase.execSQL(makeDBRBuf);
//        mDatabase.execSQL(makeDBSBuf);
//        mDatabase.execSQL(makeDBRSBuf);


    }

    public long insertRoute(ContentValues addRowValue) {
        return mDatabase.insert(TABLE_NAMER, null, addRowValue);
    }
    public long insertStation(ContentValues addRowValue) {
        return mDatabase.insert(TABLE_NAMES, null, addRowValue);
    }
    public long insertRouteStation(ContentValues addRowValue) {
        return mDatabase.insert(TABLE_NAMERS, null, addRowValue);
    }
    public long insertConfig(ContentValues addRowValue) {
        return mDatabase.insert(TABLE_NAMERC, null, addRowValue);
    }

    public Cursor queryRoute(String[] columns,
                             String selection,
                             String[] selectionArgs,
                             String groupBy,
                             String having,
                             String orderBy) {

        return mDatabase.query(TABLE_NAMER,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    public Cursor queryStation(String[] columns,
                               String selection,
                               String[] selectionArgs,
                               String groupBy,
                               String having,
                               String orderBy) {

        return mDatabase.query(TABLE_NAMES,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    public Cursor queryRouteStation(String[] columns,
                                    String selection,
                                    String[] selectionArgs,
                                    String groupBy,
                                    String having,
                                    String orderBy) {

        return mDatabase.query(TABLE_NAMERS,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    public Cursor queryConfig(String[] columns,
                              String selection,
                              String[] selectionArgs,
                              String groupBy,
                              String having,
                              String orderBy) {

        return mDatabase.query(TABLE_NAMERC,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    public int deleteRoute(String whereClause,
                           String[] whereArgs) {
        return mDatabase.delete(TABLE_NAMER, whereClause, whereArgs);
    }
    public int deleteStation(String whereClause,
                             String[] whereArgs) {
        return mDatabase.delete(TABLE_NAMES, whereClause, whereArgs);
    }
    public int deleteRouteStation(String whereClause,
                                  String[] whereArgs) {
        return mDatabase.delete(TABLE_NAMERS, whereClause, whereArgs);
    }
    public int deleteConfig(String whereClause,
                            String[] whereArgs) {
        return mDatabase.delete(TABLE_NAMERC, whereClause, whereArgs);
    }

}
