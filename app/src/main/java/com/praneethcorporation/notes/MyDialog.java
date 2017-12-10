package com.praneethcorporation.notes;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.praneethcorporation.notes.data.NoteAdapter;
import com.praneethcorporation.notes.data.NoteContract;
import com.praneethcorporation.notes.data.NoteDbHelper;

import java.util.Calendar;

/**
 * Created by user on 9/30/2017.
 */

public class MyDialog extends DialogFragment {

    Button cancel, done;
    EditText et_title, et_content;
    String title, content;
    Commmunicator commmunicator;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        commmunicator = (Commmunicator) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.dialog, null);

        cancel = (Button) view.findViewById(R.id.cancel);
        done = (Button) view.findViewById(R.id.done);

        et_title = (EditText) view.findViewById(R.id.et_title);
        et_content = (EditText) view.findViewById(R.id.et_content);

        setCancelable(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commmunicator.onDialogMessage("Cancel was Clicked");
                dismiss();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        title = et_title.getText().toString().trim();
                                        content = et_content.getText().toString().trim();


                                        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


                                        if (title.matches("") || content.matches("")) {
                                            Toast.makeText(view.getContext(), "Fields Were Empty", Toast.LENGTH_LONG).show();
                                            commmunicator.onDialogMessage("Fields Were Empty");
                                            dismiss();
                                        } else {

                                            NoteDbHelper noteDbHelper = new NoteDbHelper(getActivity().getApplicationContext());
                                            SQLiteDatabase sqLiteDatabase = noteDbHelper.getWritableDatabase();

                                            ContentValues cv = new ContentValues();
                                            cv.put(NoteContract.NoteEntry.COLUMN_TITLE, title);
                                            cv.put(NoteContract.NoteEntry.COLUMN_CONTENT, content);
                                            cv.put(NoteContract.NoteEntry.COLUMN_TIME, mydate);

                                            sqLiteDatabase.insert(NoteContract.NoteEntry.TABLE_NAME, null, cv);

                                            dismiss();
                                            commmunicator.onDialogMessage(title);
                                        }
                                    }
                                }
        );


        return view;
    }


    interface Commmunicator {
        public void onDialogMessage(String Message);
    }
}
