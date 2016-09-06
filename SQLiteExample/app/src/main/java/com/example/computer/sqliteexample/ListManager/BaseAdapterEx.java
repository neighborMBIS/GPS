/*
package com.example.computer.sqliteexample.ListManager;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;import com.example.computer.myapplication.R;import com.example.computer.myapplication.Student;

public class BaseAdapterEx extends BaseAdapter
{
    Context             mContext        = null;
    ArrayList<Student> mData           = null;
    LayoutInflater      mLayoutInflater = null;
    
    public BaseAdapterEx( Context context, ArrayList<Student> data )
    {
        mContext        = context;
        mData           = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    
    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Student getItem( int position )
    {
        return mData.get( position );
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }

    class ViewHolder
    {
        TextView mNameTv;
        TextView mNumberTv;
        TextView mDepartmentTv;
    }
    
    @Override
    public View getView( int position,
                         View convertView,
                         ViewGroup parent )
    {
        View        itemLayout = convertView;
        ViewHolder  viewHolder = null;
        
        // 1. 어뎁터뷰가 재사용할 뷰를 넘겨주지 않은 경우에만 새로운 뷰를 생성한다. 
        // ====================================================================
        if( itemLayout == null )
        {
            itemLayout = mLayoutInflater.inflate(
                                         R.layout.list_view_item_layout, null);
            
            // View Holder를 생성하고 사용할 자식뷰를 찾아 View Holder에 참조시킨다.
            // 생성된 View Holder는 아이템에 설정해 두고, 다음에 아이템 재사용시
            // 참조한다.
            // ----------------------------------------------------------------
            viewHolder = new ViewHolder();
            
            viewHolder.mNameTv = 
                          (TextView) itemLayout.findViewById( R.id.name_text );
            viewHolder.mNumberTv = 
                        (TextView) itemLayout.findViewById( R.id.number_text );
            viewHolder.mDepartmentTv = 
                    (TextView) itemLayout.findViewById( R.id.department_text );

            itemLayout.setTag( viewHolder );
            // ----------------------------------------------------------------
        }
        else
        {
            // 재사용 아이템에는 이전에 View Holder 객체를 설정해 두었다. 
            // 그러므로 설정된 View Holder 객체를 이용해서 findViewById 함수를 
            // 사용하지 않고 원하는 뷰를 참조할 수 있다.
            viewHolder = (ViewHolder)itemLayout.getTag();
        }
        // ====================================================================
        
        // 2. 이름, 학번, 학과 데이터를 참조하여 레이아웃을 갱신한다. 
        // ====================================================================
        viewHolder.mNameTv.setText( mData.get( position ).mName );
        viewHolder.mNumberTv.setText( mData.get( position ).mNumber );
        viewHolder.mDepartmentTv.setText( mData.get( position ).mDepartment );
        // ====================================================================
        
        return itemLayout;
    }
    
    public void add( int index, Student addData )
    {
        mData.add( index, addData );
        notifyDataSetChanged();
    }
    
    public void delete( int index )
    {
        mData.remove( index );
        notifyDataSetChanged();
    }
    
    public void clear( )
    {
        mData.clear();
        notifyDataSetChanged();
    }
}
*/
