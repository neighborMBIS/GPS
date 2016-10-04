/*

package com.example.computer.sqliteexample.ListManager;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.computer.sqliteexample.R;

public class CursorAdapterEx extends CursorAdapter
{
    @SuppressWarnings("deprecation")
    public CursorAdapterEx(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById( R.id.tv_name );
        TextView tvAge = (TextView) view.findViewById( R.id.tv_age );

        String name = cursor.getString( cursor.getColumnIndex( "name" ) );
        String age = cursor.getString( cursor.getColumnIndex( "age" ) );

        Log.d("스트링 확인", name + ", " + age);

        tvName.setText( name );
        tvAge.setText( age );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.text_view, parent, false );
        return v;
    }

}






*/
/*
public class CursorAdapterEx extends CursorAdapter
{
    private static Uri PROVIDER_URI = Uri.parse(  
                        "content://com.superdroid.StudentsProvider/students");
    
    Context             mContext        = null;
    LayoutInflater      mLayoutInflater = null;
        
    Handler             mHandler        = new Handler();
    
    public CursorAdapterEx( Context context, Cursor c, int flags )
    {
        super( context, c, flags );
        
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    class ViewHolder
    {
        TextView mNameTv;
        TextView mNumberTv;
        TextView mDepartmentTv;
    }

    @Override
    public View newView( Context context,
                         Cursor cursor,
                         ViewGroup parent )
    {
        // 1. 새로운 아이템 뷰를 생성한다.
        // ====================================================================
        View itemLayout = mLayoutInflater.inflate(
                                         R.layout.list_view_item_layout, null);
        // ====================================================================
        
        // 2. 아이템에 뷰홀더를 설정한다.
        // ====================================================================
        ViewHolder viewHolder = new ViewHolder();
        
        viewHolder.mNameTv = 
                      (TextView) itemLayout.findViewById( R.id.name_text );
        viewHolder.mNumberTv = 
                    (TextView) itemLayout.findViewById( R.id.number_text );
        viewHolder.mDepartmentTv = 
                (TextView) itemLayout.findViewById( R.id.department_text );

        itemLayout.setTag( viewHolder );
        // ====================================================================

        return itemLayout;
    }

    @Override
    public void bindView( View view,
                          Context context,
                          Cursor cursor )
    {
        // 1. 아이템 뷰에 저장된 뷰홀더를 얻어온다.
        // ====================================================================
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        // ====================================================================
        
        // 2. 현재 커서 위치에 이름, 학번, 학과 데이터를 얻어온다.
        // ====================================================================
        String id = cursor.getString( cursor.getColumnIndex( "_id" ));
        String name = cursor.getString( cursor.getColumnIndex( "name" ));
        String number = cursor.getString(cursor.getColumnIndex( "number" ));
        String department = cursor.getString( cursor.getColumnIndex( "department" ));
        // ====================================================================
        
        // 3. 이름, 학번, 학과 데이터를 뷰에 적용한다.  
        // ====================================================================
        viewHolder.mNameTv.setText( name + "(" + id + ")");
        viewHolder.mNumberTv.setText( number );
        viewHolder.mDepartmentTv.setText( department );
        // ====================================================================
    }
    
    @Override
    protected void onContentChanged()
    {
        super.onContentChanged();

        new Thread()
        {
            public void run()
            {
                String[] columns = 
                        new String[] { "_id", "number", "name", "department"};

                final Cursor data = mContext.getContentResolver().query( 
                                                           PROVIDER_URI, 
                                                           columns, 
                                                           null, 
                                                           null, 
                                                           null );
                mHandler.post( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        changeCursor( data );
                    }
                } );
            }
        }.start();
    }
    
    public void add( String name, String number, String department )
    {
        final ContentValues values = new ContentValues();
        
        values.put( "name", name );
        values.put( "number", number );
        values.put( "department", department );

        new Thread()
        {
            public void run()
            {
                mContext.getContentResolver().insert( PROVIDER_URI, values);
            }
        }.start();
    }
    
    public void remove( int id )
    {
        final Uri deleteUri = ContentUris.withAppendedId( PROVIDER_URI, id );
        
        new Thread()
        {
            public void run()
            {
                mContext.getContentResolver().delete( deleteUri, null, null );
            }
        }.start();
    }
    
    public void clear()
    {
        new Thread()
        {
            public void run()
            {
                mContext.getContentResolver().delete( PROVIDER_URI, null, null );
            }
        }.start();
    }
    
    static boolean togleOrder = true;
    
    public void sort()
    {   
        final String[] columns = new String[] { "_id", "number", "name", "department"};
        
        new Thread()
        {
            public void run()
            {
                String order = "";
                
                if( togleOrder == true )
                {
                    order = "name DESC";
                    togleOrder = false;
                }
                else
                {
                    order = "name ASC";
                    togleOrder = true;
                }
                
                final Cursor data = mContext.getContentResolver().query( 
                                                                PROVIDER_URI, 
                                                                columns, 
                                                                null, 
                                                                null, 
                                                                order );
                
                mHandler.post( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        changeCursor( data );
                    }
                } );
            }
        }.start();
    }
}
*/

