package com.opencode.myapp.bdsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class DBMetodos extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_CAPTURAS ="create table " + DBDatos.TABLE_CAPTURAS+ "(" +
            "_id integer primary key autoincrement,"+
            DBDatos.RUT + " text,"+
            DBDatos.NOMBRE + " text,"+
            DBDatos.EMPRESA + " text,"+
            DBDatos.CUARTEL + " text,"+
            DBDatos.TELEFONO + " text,"+
            DBDatos.NOSINC + " text,"+
            DBDatos.PRODUCTO + " text,"+
            DBDatos.VARIEDAD + " text,"+
            DBDatos.CANTIDAD +" text,"+
            DBDatos.SINCRONIZADO+" text)";

    private static final String CREATE_TABLE_USUARIOS ="create table " + DBDatos.TABLE_USUARIOS + "(" +
            DBDatos.USUARIO+" text,"+
            DBDatos.PASSWORD+" text)";

    private static final String CREATE_TABLE_SINCRONIZADO ="create table " + DBDatos.TABLE_SINCRONIZADO + "(" +
            DBDatos.IDSINCRONIZADO+" integer,"+
            DBDatos.NOSINCRONIZADO+" integer)";

    private static final String DROP_TABLE_CAPTURAS = "drop table if exists " + DBDatos.TABLE_CAPTURAS;
    private static final String DROP_TABLE_USUARIOS = "drop table if exists " + DBDatos.TABLE_USUARIOS;
    private static final String DROP_TABLE_SINCRONIZADO = "drop table if exists " + DBDatos.TABLE_SINCRONIZADO;

    private static final int DATABASE_VERSION = 12;

    private SQLiteDatabase data;

    public DBMetodos(@Nullable Context context) {
        super(context, DBDatos.DATABASE_NAME, null, DATABASE_VERSION);    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_CAPTURAS);
        db.execSQL(CREATE_TABLE_USUARIOS);
        db.execSQL(CREATE_TABLE_SINCRONIZADO);
        db.execSQL("INSERT INTO "+DBDatos.TABLE_USUARIOS+" VALUES ('ADMIN', 'TAQA')");
        db.execSQL("INSERT INTO "+DBDatos.TABLE_SINCRONIZADO+" VALUES (0,0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,  int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CAPTURAS);
        db.execSQL(DROP_TABLE_USUARIOS);
        db.execSQL(DROP_TABLE_SINCRONIZADO);
        onCreate(db);
    }

    public boolean validarUsuario(String usuario,String password,  SQLiteDatabase db){
        String[] projection = {DBDatos.USUARIO};
        String seleccion = DBDatos.USUARIO+"=? and "+DBDatos.PASSWORD+"=?";
        String [] argumentos={usuario,password};

        int count = (db.query(DBDatos.TABLE_USUARIOS,projection,
                seleccion,argumentos,null,null,null).getCount());
        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    public void ReiniciaIDSincronizado(SQLiteDatabase db)
    {
        db.execSQL(DROP_TABLE_SINCRONIZADO);
        db.execSQL(CREATE_TABLE_SINCRONIZADO);
        db.execSQL("INSERT INTO "+DBDatos.TABLE_SINCRONIZADO+" VALUES (0,0)");
    }

    public int ObtieneIDSincronizado(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {DBDatos.IDSINCRONIZADO};
        String seleccion = null;
        String [] argumentos=null;

        int count = (db.query(DBDatos.TABLE_SINCRONIZADO,projection,
                seleccion,argumentos,null,null,null).getCount());
        return count;
    }

    public void GuardaIDSincronizado(SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBDatos.NOSINCRONIZADO, 0);
        //id local auto_increment..
        db.insert(DBDatos.TABLE_SINCRONIZADO, null, contentValues);
        //Toast.makeText(context,"Guardado en local", Toast.LENGTH_SHORT).show();
    }

    private SQLiteDatabase openDatabase(){
        String path = DBDatos.DATABASE_NAME;
        data = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return data;
    }
    public void guardarcaptura(
            String rut,
            String nombre,
            String empresa,
            String cuartel,
            String telefono,
            String nosinc,
            String producto,
            String variedad,
            String cantidad,
            String sincronizado,
            SQLiteDatabase db)
    {
        //
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBDatos.RUT, rut);
        contentValues.put(DBDatos.NOMBRE, nombre);
        contentValues.put(DBDatos.EMPRESA, empresa);
        contentValues.put(DBDatos.CUARTEL, cuartel);
        contentValues.put(DBDatos.TELEFONO, telefono);
        contentValues.put(DBDatos.NOSINC, nosinc);
        contentValues.put(DBDatos.PRODUCTO, producto);
        contentValues.put(DBDatos.VARIEDAD, variedad);
        contentValues.put(DBDatos.CANTIDAD, cantidad);
        contentValues.put(DBDatos.SINCRONIZADO, sincronizado);

        //id local auto_increment..
        db.insert(DBDatos.TABLE_CAPTURAS, null, contentValues);
        //Toast.makeText(context,"Guardado en local", Toast.LENGTH_SHORT).show();
    }

    public void updateSincronizados(String id, String sincronizado, SQLiteDatabase db){
        //
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBDatos.SINCRONIZADO, sincronizado);
        db.update(DBDatos.TABLE_CAPTURAS, contentValues, DBDatos.IDCAPTURA + "=?", new String[]{id} );
        //Toast.makeText(context,"Guardado en local", Toast.LENGTH_SHORT).show();
    }

    //selecciona todos los clientes
    public Cursor leercapturas(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                DBDatos.IDCAPTURA,
                DBDatos.RUT,
                DBDatos.NOMBRE,
                DBDatos.EMPRESA,
                DBDatos.CUARTEL,
                DBDatos.TELEFONO,
                DBDatos.NOSINC,
                DBDatos.PRODUCTO,
                DBDatos.VARIEDAD,
                DBDatos.CANTIDAD,
                DBDatos.SINCRONIZADO};

        String seleccion = DBDatos.SINCRONIZADO+"=?";
        String [] argumentos={"0"};

        return (db.query(DBDatos.TABLE_CAPTURAS,projection,seleccion,argumentos,null,null,DBDatos.NOMBRE+" ASC"));
    }

    public ArrayList<HashMap<String, String>> Llenar_Array_Capturas(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> capturasList = new ArrayList<>();
        String estado="0";
        String query = "SELECT id,rut, nombre, empresa,cuartel,telefono,nosinc,producto,variedad,cantidad FROM "+ DBDatos.TABLE_CAPTURAS+
                       " WHERE sincronizado="+estado;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> captura = new HashMap<>();
            captura.put("id",cursor.getString(cursor.getColumnIndex(DBDatos.IDCAPTURA)));
            captura.put("rut",cursor.getString(cursor.getColumnIndex(DBDatos.RUT)));
            captura.put("nombre",cursor.getString(cursor.getColumnIndex(DBDatos.NOMBRE)));
            captura.put("empresa",cursor.getString(cursor.getColumnIndex(DBDatos.EMPRESA)));
            captura.put("cuartel",cursor.getString(cursor.getColumnIndex(DBDatos.CUARTEL)));
            captura.put("telefono",cursor.getString(cursor.getColumnIndex(DBDatos.TELEFONO)));
            captura.put("nosinc",cursor.getString(cursor.getColumnIndex(DBDatos.NOSINC)));
            captura.put("producto",cursor.getString(cursor.getColumnIndex(DBDatos.PRODUCTO)));
            captura.put("variedad",cursor.getString(cursor.getColumnIndex(DBDatos.VARIEDAD)));
            captura.put("cantidad",cursor.getString(cursor.getColumnIndex(DBDatos.CANTIDAD)));
            capturasList.add(captura);
        }
        return  capturasList;
    }

    public int Modificar_Captura_x_id(
            int id,
            String empresa,
            String cuartel,
            String telefono,
            String nosinc,
            String producto,
            String variedad,
            String cantidad)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(DBDatos.EMPRESA, empresa);
        cVals.put(DBDatos.CUARTEL, cuartel);
        cVals.put(DBDatos.TELEFONO, telefono);
        cVals.put(DBDatos.NOSINC, nosinc);
        cVals.put(DBDatos.PRODUCTO, producto);
        cVals.put(DBDatos.VARIEDAD, variedad);
        cVals.put(DBDatos.CANTIDAD, cantidad);
        int count = db.update(DBDatos.TABLE_CAPTURAS, cVals,  DBDatos.IDCAPTURA+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }

    public void Eliminar_Captura_x_id(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e(TAG, "+++_id : " + id);

        db.delete(DBDatos.TABLE_CAPTURAS,  DBDatos.IDCAPTURA+"="+id, null);
    }
}
