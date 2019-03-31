package com.hyy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/* 诗词库列表的适配器 */
public class PoemLibListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private String [] data;

    public PoemLibListAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    public void setData(String [] data)
    {
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        String title = (String)getItem(i);
        if(view == null)
        {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_poem_lib_list,null);
            viewHolder.tvTitle = view.findViewById(R.id.poem_lib_name);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        title = title.replace("$","");
        viewHolder.tvTitle.setText(title);

        return view;
    }

    class ViewHolder
    {
        TextView tvTitle;
    }
}
