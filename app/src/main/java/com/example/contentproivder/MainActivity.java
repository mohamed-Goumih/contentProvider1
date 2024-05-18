package com.example.contentproivder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Insertion d'un nouvel item
        ContentValues values = new ContentValues();
        values.put(MyContentContract.ItemEntry.COLUMN_NAME_TITLE, "Titre de l'article");
        values.put(MyContentContract.ItemEntry.COLUMN_NAME_DESCRIPTION, "Description de l'article");

        Uri newItemUri = getContentResolver().insert(MyContentContract.ItemEntry.CONTENT_URI, values);

// Requête pour obtenir tous les items
        Cursor cursor = getContentResolver().query(MyContentContract.ItemEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MyContentContract.ItemEntry.COLUMN_NAME_TITLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(MyContentContract.ItemEntry.COLUMN_NAME_DESCRIPTION));
                // Traitez les données ici
                Log.d("TAG",title+" "+description);
            }
            cursor.close();
        }

// Mise à jour d'un item
        ContentValues updateValues = new ContentValues();
        updateValues.put(MyContentContract.ItemEntry.COLUMN_NAME_DESCRIPTION, "Nouvelle description");

        String selection = MyContentContract.ItemEntry._ID + "=?";
        String[] selectionArgs = { "1" };

        int count = getContentResolver().update(MyContentContract.ItemEntry.CONTENT_URI, updateValues, selection, selectionArgs);

// Suppression d'un item
        String deleteSelection = MyContentContract.ItemEntry._ID + "=?";
        String[] deleteSelectionArgs = { "1" };

        int rowsDeleted = getContentResolver().delete(MyContentContract.ItemEntry.CONTENT_URI, deleteSelection, deleteSelectionArgs);

    }
}