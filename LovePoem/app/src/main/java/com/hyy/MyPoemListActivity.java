package com.hyy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 我写的诗词列表界面
 */
public class MyPoemListActivity extends AppCompatActivity {

    private ListView listView;
    private DBHelper dbHelper ;
    private List<MyPoemBean> myPoemBeanList;
    private MyPoemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_poem_list);
        listView = findViewById(R.id.list_view_my_poem);
        dbHelper = new DBHelper(getApplicationContext());
        adapter = new MyPoemAdapter(getApplicationContext(),dbHelper);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyPoemListActivity.this,MyPoemContentActivity.class);
                MyPoemBean bean = myPoemBeanList.get(i);
                intent.putExtra("id",bean.getId());
                intent.putExtra("title",bean.getTitle());
                intent.putExtra("writer",bean.getWriter());
                intent.putExtra("content",bean.getContent());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myPoemBeanList = dbHelper.queryAllMyPoem();
        adapter.setData(myPoemBeanList);
        adapter.notifyDataSetChanged();
    }


    class MyPoemAdapter extends BaseAdapter
    {

        private LayoutInflater inflater;
        private List<MyPoemBean> data = new ArrayList<MyPoemBean>();
        DBHelper dbHelper;
        Context context;

        public MyPoemAdapter(Context context,DBHelper dbHelper)
        {
            inflater = LayoutInflater.from(context);
            this.dbHelper = dbHelper;
            this.context = context;
        }

        public void setData(List<MyPoemBean> data)
        {
            this.data = data;
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            final MyPoemBean bean = (MyPoemBean) getItem(i);
            if(view == null)
            {
                viewHolder = new ViewHolder();
                view = inflater.inflate(R.layout.item_my_poem_list,null);
                viewHolder.tvTitle = view.findViewById(R.id.tv_title);

                view.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tvTitle.setText((i+1)+". "+bean.getTitle());
            return view;
        }
    }

    class ViewHolder
    {
        TextView tvTitle;
    }



}
