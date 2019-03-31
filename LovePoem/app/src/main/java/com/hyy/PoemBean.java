package com.hyy;

/**
 * 诗词实体类，用于保存一首诗词的内容，包括类别和内容
 */

public class PoemBean {
    private int id;
    private String poemClass;
    private String poemContent;
    private int isCollect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoemClass() {
        return poemClass;
    }

    public void setPoemClass(String poemClass) {
        this.poemClass = poemClass;
    }

    public String getPoemContent() {
        return poemContent;
    }

    public void setPoemContent(String poemContent) {
        this.poemContent = poemContent;
    }

    public int isCollect() {
        return isCollect;
    }

    public void setCollect(int collect) {
        isCollect = collect;
    }
}
