package com.hyy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.hyy.bean.FileBean;
import com.hyy.bean.SimpleTreeAdapter;
import com.hyy.tree.bean.Node;
import com.hyy.tree.bean.TreeListViewAdapter;


import java.util.ArrayList;
import java.util.List;

public class PoemCollegeActivity extends AppCompatActivity{

    private List<FileBean> mDatas = new ArrayList<FileBean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;
    private String [] poemIntroduction ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_college);
        poemIntroduction = getResources().getStringArray(R.array.poem_college);

        initDatas();
        mTree = findViewById(R.id.list_view_menu);
        try
        {

            mAdapter = new SimpleTreeAdapter<FileBean>(mTree, this, mDatas, 10);
            mTree.setAdapter(mAdapter);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                String content = poemIntroduction[node.getId()];
                if(content.length() > 2) {
                    Intent intent = new Intent(PoemCollegeActivity.this,PoemKnowledgeActivity.class);
                    intent.putExtra("content",content);
                    startActivity(intent);
                }
            }
        });


    }


    private void initDatas()
    {

        // id , pid , label , 其他属性
        mDatas.add(new FileBean(1, 0, "声律"));
        mDatas.add(new FileBean(2, 0, "用韵"));
        mDatas.add(new FileBean(3, 0, "体裁"));
        mDatas.add(new FileBean(4, 0, "对仗"));

        mDatas.add(new FileBean(5, 1, "声调"));
        mDatas.add(new FileBean(6, 1, "音韵"));
        mDatas.add(new FileBean(7, 1, "格律"));
        mDatas.add(new FileBean(8, 7, "粘对"));
        mDatas.add(new FileBean(9, 7, "孤平"));

        mDatas.add(new FileBean(10, 2, "押韵"));
        mDatas.add(new FileBean(11, 2, "韵部"));
        mDatas.add(new FileBean(12, 2, "唱和"));
        mDatas.add(new FileBean(13, 2, "平水韵"));
        mDatas.add(new FileBean(14, 2, "中华新韵"));

        mDatas.add(new FileBean(15, 3, "诗歌形式"));
        mDatas.add(new FileBean(16, 15, "古体诗"));
        mDatas.add(new FileBean(17, 15, "近体诗"));
        mDatas.add(new FileBean(18, 15, "词"));
        mDatas.add(new FileBean(19, 15, "曲"));
        mDatas.add(new FileBean(20, 3, "诗歌体裁"));
        mDatas.add(new FileBean(21, 20, "写景抒情诗"));
        mDatas.add(new FileBean(22, 20, "咏物言志诗"));
        mDatas.add(new FileBean(23, 20, "怀古咏史诗"));
        mDatas.add(new FileBean(24, 20, "边塞征战诗"));

        mDatas.add(new FileBean(25, 4, "宽对"));
        mDatas.add(new FileBean(26, 4, "邻对"));
        mDatas.add(new FileBean(27, 4, "自对"));
        mDatas.add(new FileBean(28, 4, "借对"));

    }


}
