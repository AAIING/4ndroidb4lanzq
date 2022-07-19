package com.opencode.myapp.bdsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ItemsidData {

    private SQLiteDatabase db;
    private DBMetodos dbHelper;

    public ItemsidData(Context context) {
        dbHelper = new DBMetodos(context);
    }


    public void getItem(String idpedido) throws  Exception{



    }

    public void insertItem(String idpedido, String tipo, String uuid) throws Exception{
        db = dbHelper.getWritableDatabase();
        try {
            String query3 = "INSERT INTO ITEMSID ( " +
                    "PEDIDOSREGISTRO, TIPOITEM, CODIGO)" +
                    "VALUES (?, ?);";
            db.execSQL(
                    query3,
                    new Object[]{
                            idpedido,
                            tipo,
                            uuid
                    });
            dbHelper.close();
        }catch (Exception e){
            dbHelper.close();
            throw new Exception(e.getMessage());
        }
    }

}
