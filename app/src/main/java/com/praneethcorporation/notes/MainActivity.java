package com.praneethcorporation.notes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.praneethcorporation.notes.data.NoteAdapter;
import com.praneethcorporation.notes.data.NoteContract;
import com.praneethcorporation.notes.data.NoteDbHelper;

public class MainActivity extends AppCompatActivity implements MyDialog.Commmunicator {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        RelativeLayout empty_view = (RelativeLayout) findViewById(R.id.emptyView);
        list.setEmptyView(empty_view);

        final NoteDbHelper noteDbHelper = new NoteDbHelper(this);
        final SQLiteDatabase sqliteDatabase = noteDbHelper.getReadableDatabase();
        final Cursor cursor = sqliteDatabase.rawQuery("SELECT * FROM " + NoteContract.NoteEntry.TABLE_NAME, null);
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), cursor);
        list.setAdapter(noteAdapter);
        noteAdapter.changeCursor(cursor);
        noteAdapter.notifyDataSetChanged();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title = (TextView) view.findViewById(R.id.titleoflistitem);
                TextView content = (TextView) view.findViewById(R.id.contentoflistiten);
                TextView time = (TextView) view.findViewById(R.id.time);


                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.contentdialog);

                TextView Dtitle = (TextView) dialog.findViewById(R.id.tv_title);
                TextView Dcontent = (TextView) dialog.findViewById(R.id.tv_content);
                TextView Dtime = (TextView) dialog.findViewById(R.id.tv_time);
                Button done = (Button) dialog.findViewById(R.id.done);

                Dtitle.setText(title.getText().toString().trim());
                Dcontent.setText(content.getText().toString().trim());
                Dtime.setText(time.getText().toString().trim());

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setTitle("Note");
                dialog.setCancelable(true);
                dialog.show();
            }
        });


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);


                TextView title = (TextView) view.findViewById(R.id.titleoflistitem);
                TextView content = (TextView) view.findViewById(R.id.contentoflistiten);
                TextView time = (TextView) view.findViewById(R.id.time);


                final String Sttitle = title.getText().toString();
                final String Scontent = content.getText().toString();
                final String Stime = time.getText().toString();

                // Setting Dialog Title
                alertDialog.setTitle("Confirm Delete...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete this?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.deletebig);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        NoteDbHelper noteDbHelper = new NoteDbHelper(MainActivity.this);
                        SQLiteDatabase msqLiteDatabase = noteDbHelper.getWritableDatabase();

                        msqLiteDatabase.execSQL("DELETE  FROM " + NoteContract.NoteEntry.TABLE_NAME + " WHERE " + NoteContract.NoteEntry.COLUMN_TIME + " = '" + Stime + "'");

                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivitymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.AddNote:
                showdialog();
                return true;
            case R.id.deleteAll:
                deleteAll();
                return true;
        }

        return true;
    }

    private void deleteAll() {
        NoteDbHelper noteDbHelper = new NoteDbHelper(this);
        SQLiteDatabase sqliteDatabase = noteDbHelper.getWritableDatabase();
        sqliteDatabase.execSQL("DELETE FROM " + NoteContract.NoteEntry.TABLE_NAME);
        Cursor cursor = sqliteDatabase.rawQuery("SELECT * FROM " + NoteContract.NoteEntry.TABLE_NAME, null);
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), cursor);
        list.setAdapter(noteAdapter);
        noteAdapter.changeCursor(cursor);
        noteAdapter.notifyDataSetChanged();
        super.onRestart();
        Intent i = new Intent(MainActivity.this, MainActivity.class);  //your class
        startActivity(i);
        finish();


    }

    private void showdialog() {
        android.app.FragmentManager manager = getFragmentManager();
        MyDialog myDialog = new MyDialog();
        myDialog.show(manager, "My Dialog");
    }


    @Override
    public void onDialogMessage(String Message) {

        if (Message.matches("Fields Were Empty")) {
            Toast.makeText(this, Message, Toast.LENGTH_LONG).show();
            showdialog();
        } else {
            Toast.makeText(this, Message, Toast.LENGTH_LONG).show();
            super.onRestart();
            Intent i = new Intent(MainActivity.this, MainActivity.class);  //your class
            startActivity(i);
            finish();
        }
    }
}
