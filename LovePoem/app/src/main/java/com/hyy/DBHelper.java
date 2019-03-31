package com.hyy;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类，用来创建数据库和表格，集成对数据库的增删改查
 */
public class DBHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "poemsDB";//数据库名称
	private final static int VERSION = 3;

	//数据库字段名称，定义为常量，方便使用
	public final static String TABLE_NAME = "poemTable";

	public final static String FIELD_ID = "_id";
    public  final static String FIELD_POEM_CLASS = "poem_class";
    public final static String FIELD_POEM_CONTENT = "poem_content";
    public final static String FILE_IS_COLLECT = "is_collect";
   private String sql1 = "create table if not exists "+TABLE_NAME+"(" +
            "_id integer primary key autoincrement , " +
            FIELD_POEM_CLASS +" text," +
            FIELD_POEM_CONTENT+" text," +
            FILE_IS_COLLECT +" int"+
            ");"; // 创建 表格


	public final static String TABLE_NAME_MY_POEM = "myPoem";
	public final static String FIELD_TITLE = "title";
	public final static String FIELD_WRITER = "writer";
	public final static String FIELD_CONTENT = "content";
	private String sql2 = "create table if not exists "+TABLE_NAME_MY_POEM+"("+
			"_id integer primary key autoincrement , " +
			FIELD_TITLE +" text,"+
			FIELD_WRITER +" text,"+
			FIELD_CONTENT+" text"+");";


	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}


	@Override // 创建数据库
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql1);
		db.execSQL(sql2);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	    if(newVersion > oldVersion) {
            db.execSQL("drop table if exists " + TABLE_NAME);
			db.execSQL("drop table if exists " + TABLE_NAME_MY_POEM);
            db.execSQL(sql1);
			db.execSQL(sql2);
        }

	}

	//下面是基础数据库相关操作，增删改查

	/**
	 * 插入信息到数据库中
	 * @param bean
	 */
	public void add(PoemBean bean)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(FIELD_POEM_CLASS,bean.getPoemClass());
		contentValues.put(FIELD_POEM_CONTENT,bean.getPoemContent());
		contentValues.put(FILE_IS_COLLECT,bean.isCollect());
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TABLE_NAME, null, contentValues);
		db.close();
	}
   /**
	* 自创诗词添加到数据库
   * */
	public void add(MyPoemBean bean)
	{
		ContentValues cv = new ContentValues();
		cv.put(FIELD_TITLE,bean.getTitle());
		cv.put(FIELD_WRITER,bean.getWriter());
		cv.put(FIELD_CONTENT,bean.getContent());
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TABLE_NAME_MY_POEM, null, cv);
		db.close();
	}
	/**
	 * 删除信息
	 * @param id
	 */
	public void delete(String tableName,int id)
	{
		String sql= "_id=?";
		SQLiteDatabase db = getWritableDatabase();
		db.delete(tableName, sql, new String[] {""+id});
		db.close();
	}
	/**
	 * 更改信息
	 * @param bean
	 */
	public void update(PoemBean bean)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(FIELD_POEM_CLASS,bean.getPoemClass());
		contentValues.put(FIELD_POEM_CONTENT,bean.getPoemContent());
        contentValues.put(FILE_IS_COLLECT,bean.isCollect());
		SQLiteDatabase db = getWritableDatabase();
		db.update(TABLE_NAME, contentValues, "_id=?", new String[] {""+bean.getId()});
		db.close();
	}

	public void update(MyPoemBean bean)
	{
		ContentValues cv = new ContentValues();
		cv.put(FIELD_TITLE,bean.getTitle());
		cv.put(FIELD_WRITER,bean.getWriter());
		cv.put(FIELD_CONTENT,bean.getContent());
		SQLiteDatabase db = getWritableDatabase();
		db.update(TABLE_NAME_MY_POEM,cv,"_id=?" , new String[] {""+bean.getId()});
		db.close();
	}
	/**
	 * 查询所有
	 * @return 所有信息列表
	 */
	public List<PoemBean> queryAll()
	{
		List<PoemBean> data = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.query(TABLE_NAME, null,null, null,null,null,null,null);
			if(cursor != null && cursor.getCount() > 0)
			{
				while(cursor.moveToNext())
				{
					PoemBean bean = new PoemBean();
                    bean.setId(cursor.getInt(cursor.getColumnIndex(FIELD_ID)));
                    bean.setPoemClass(cursor.getString(cursor.getColumnIndex(FIELD_POEM_CLASS)));
                    bean.setPoemContent(cursor.getString(cursor.getColumnIndex(FIELD_POEM_CONTENT)));
                    bean.setCollect(cursor.getInt(cursor.getColumnIndex(FILE_IS_COLLECT)));
					data.add(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(cursor != null)
        cursor.close();
		db.close();
		return data;
	}

	public List<MyPoemBean> queryAllMyPoem()
    {
        List<MyPoemBean> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME_MY_POEM, null,null, null,null,null,null,null);
            if(cursor != null && cursor.getCount() > 0)
            {
                while(cursor.moveToNext())
                {
                    MyPoemBean bean = new MyPoemBean();
                    bean.setId(cursor.getInt(cursor.getColumnIndex(FIELD_ID)));
                    bean.setTitle(cursor.getString(cursor.getColumnIndex(FIELD_TITLE)));
                    bean.setWriter(cursor.getString(cursor.getColumnIndex(FIELD_WRITER)));
                    bean.setContent(cursor.getString(cursor.getColumnIndex(FIELD_CONTENT)));
                    data.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(cursor != null)
            cursor.close();
        db.close();
        return data;
    }


	/**
	 * 按id查询信息
	 * @param
	 * @return
	 */
	public List<PoemBean> rawQuery(String fieldName,String value)
	{
		List<PoemBean> data = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("select * from "+TABLE_NAME+" where "+fieldName+"=?",new String[] {value});
			if(cursor != null && cursor.getCount() > 0)
			{
				while(cursor.moveToNext())
				{
					PoemBean bean = new PoemBean();
					bean.setId(cursor.getInt(cursor.getColumnIndex(FIELD_ID)));
					bean.setPoemClass(cursor.getString(cursor.getColumnIndex(FIELD_POEM_CLASS)));
					bean.setPoemContent(cursor.getString(cursor.getColumnIndex(FIELD_POEM_CONTENT)));
                    bean.setCollect(cursor.getInt(cursor.getColumnIndex(FILE_IS_COLLECT)));
                    data.add(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close();
		if(cursor != null)
		cursor.close();
		return data;
	}

	/**
	 * 按id查询信息
	 * @param fieldName 字段名
	 * @param value 字段关键字
	 * @return
	 */
	public List<PoemBean> keyWordQuery(String fieldName,String value)
	{
		List<PoemBean> data = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("select * from "+TABLE_NAME+" where "+fieldName+" like ?",new String[] {"%"+value+"%"});
			if(cursor != null && cursor.getCount() > 0)
			{
				while(cursor.moveToNext())
				{
					PoemBean bean = new PoemBean();
					bean.setId(cursor.getInt(cursor.getColumnIndex(FIELD_ID)));
					bean.setPoemClass(cursor.getString(cursor.getColumnIndex(FIELD_POEM_CLASS)));
					bean.setPoemContent(cursor.getString(cursor.getColumnIndex(FIELD_POEM_CONTENT)));
					bean.setCollect(cursor.getInt(cursor.getColumnIndex(FILE_IS_COLLECT)));
					data.add(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close();
		if(cursor != null)
			cursor.close();
		return data;
	}
}