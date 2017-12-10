package com.praneethcorporation.notes.data;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.praneethcorporation.notes.MainActivity;
import com.praneethcorporation.notes.MyDialog;
import com.praneethcorporation.notes.R;

import java.util.Calendar;

/**
 * Created by user on 9/30/2017.
 */

public class NoteAdapter extends CursorAdapter {

    Context ctx;

    public NoteAdapter(Context context, Cursor c) {
        super(context, c, 0);
        ctx = context;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.singlenoteitem, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {



        TextView Ttitle = (TextView) view.findViewById(R.id.titleoflistitem);
        TextView Ttime = (TextView)view.findViewById(R.id.time);

        TextView Tcontent = (TextView) view.findViewById(R.id.contentoflistiten);

        int titleColumnIndex = cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_TITLE);
        int contentColumnIndex = cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_CONTENT);
        int timeColumnIndex = cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_TIME);

        String title = cursor.getString(titleColumnIndex);
        String content = cursor.getString(contentColumnIndex);
        String time = cursor.getString(timeColumnIndex);


        Ttitle.setText(title);
        Tcontent.setText(content);
        Ttime.setText(time);
}
}
