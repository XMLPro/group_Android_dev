package com.example.suzuki.memoprot001;

/**
 * Created by suzuki on 2015/10/11.
 */
public class MemoListItem {
    private int id;
    private String title;
    private String date;

    public MemoListItem() {
    }

    public MemoListItem(int id, String title, String date) {
        setItems(id, title, date);
    }

    public MemoListItem(String id, String title, String date) {
        setItems(id, title, date);
    }

    public void setItems(int id, String title, String date) {
        setId(id);
        setTitle(title);
        setDate(date);
    }

    public void setItems(String id, String title, String date) {
        setItems(Integer.parseInt(id), title, date);
    }

    public String[] getItems() {
        return new String[]{String.valueOf(this.id), this.title, this.date};
    }

    public void setId(String id) {
        setId(Integer.parseInt(id));
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setTitle(String title) {
        if (title.length() < 1) {
            this.title = "nothing";
            return;
        }
        if (title.indexOf("\n") == -1) {
            this.title = title.substring(0, Math.min(title.length(), 20));
        } else {
            this.title = title.substring(0, Math.min(title.indexOf("\n"), 20));
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }
}
