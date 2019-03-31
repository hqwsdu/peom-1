package com.hyy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImportPoemActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REC_REQUESTCODE = 1;
    private TextView tvFileContent;
    private String fileName;
    private TextView tvFileName;
    StringBuilder content =  new StringBuilder();
    private DBHelper dbHelper ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_poem);
        tvFileContent = findViewById(R.id.tv_text_content);
        Button btChooseFile = findViewById(R.id.bt_choose_file);
        Button btImport = findViewById(R.id.bt_import);
        tvFileName = findViewById(R.id.tv_file_name);
        btChooseFile.setOnClickListener(this);
        btImport.setOnClickListener(this);
        dbHelper = new DBHelper(getApplicationContext());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_choose_file:
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,REC_REQUESTCODE);
                break;
            case R.id.bt_import:
                importPoem();
                break;
        }
    }

    /**
     * 导入诗词，注意保存诗词的TXT 文件的格式要求是，各诗词之间要以 $ 为分隔
     */
    private void importPoem()
    {
        //保存诗词库名到SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(MainMenuActivity.POEM_CLASS_SP,MODE_PRIVATE);
        String str = sharedPreferences.getString(MainMenuActivity.SP_KEY,"");
        if(str.contains(fileName))
        {
            Toast.makeText(getApplicationContext(),"该诗词集已经导入，不能重复导入。",Toast.LENGTH_SHORT).show();
        }
        else
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            str = str + fileName +"=";
            editor.putString(MainMenuActivity.SP_KEY,str);
            editor.apply();
            String [] poems = content.toString().split("=");
            for(String poem:poems)
            {
                if(!poem.isEmpty())
                {
                    poem = poem.replace("=", "");
                    PoemBean bean = new PoemBean();
                    bean.setPoemClass(fileName);
                    bean.setPoemContent(poem);
                    dbHelper.add(bean);//保存诗词到数据库
                }
            }
            Toast.makeText(getApplicationContext(),"导入完成",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK&&requestCode==REC_REQUESTCODE){
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            if(uri == null)
            {
                return;
            }
            String path = GetPathFromUri4kitkat.getPath(getApplicationContext(),uri);
            File file = new File(path);
            fileName = file.getName();
            tvFileName.setText(fileName);
            try {

                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream in = new BufferedInputStream(fis);
                BufferedReader reader;
                in.mark(4);
                byte[] first3bytes = new byte[3];
                in.read(first3bytes);//找到文档的前三个字节并自动判断文档类型。
                in.reset();
                if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                        && first3bytes[2] == (byte) 0xBF) {// utf-8

                    reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

                } else if (first3bytes[0] == (byte) 0xFF
                        && first3bytes[1] == (byte) 0xFE) {

                    reader = new BufferedReader(
                            new InputStreamReader(in, "unicode"));
                } else if (first3bytes[0] == (byte) 0xFE
                        && first3bytes[1] == (byte) 0xFF) {

                    reader = new BufferedReader(new InputStreamReader(in,
                            "utf-16be"));
                } else if (first3bytes[0] == (byte) 0xFF
                        && first3bytes[1] == (byte) 0xFF) {

                    reader = new BufferedReader(new InputStreamReader(in,
                            "utf-16le"));
                } else {
                    reader = new BufferedReader(new InputStreamReader(in, "GBK"));
                }
                String tempString;
                content.delete(0,content.length());//先删除原有的内容
                while ((tempString = reader.readLine()) != null) {
                    //System.out.println(tempString);
                    content.append(tempString).append("\n");
                }
                tvFileContent.setText(content.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this,"请打开读写存储的权限",Toast.LENGTH_SHORT).show();
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(localIntent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
