/*
package com.example.computer.sqliteexample.ListManager;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;import com.example.computer.myapplication.R;import com.example.computer.myapplication.Student;

public class ArrayAdapterEx extends ArrayAdapter<Student>
{


    
    public ArrayAdapterEx( Context context,
                           int resource,
                           int textViewResourceId,
                           List<Student> objects )
    {
        super( context, resource, textViewResourceId, objects );
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
        View itemLayout = super.getView( position, convertView, parent );
        
        ViewHolder  viewHolder = (ViewHolder)itemLayout.getTag();
        
        if( viewHolder == null )
        {
            viewHolder = new ViewHolder();
            viewHolder.mNameTv = 
                          (TextView) itemLayout.findViewById( R.id.name_text );
            viewHolder.mNumberTv = 
                        (TextView) itemLayout.findViewById( R.id.number_text );
            viewHolder.mDepartmentTv = 
                    (TextView) itemLayout.findViewById( R.id.department_text );

            itemLayout.setTag( viewHolder );
        }
        viewHolder.mNameTv.setText( getItem( position ).mName );
        viewHolder.mNumberTv.setText( getItem( position ).mNumber );
        viewHolder.mDepartmentTv.setText( getItem( position ).mDepartment );
        
        return itemLayout;
    }
}
*/
