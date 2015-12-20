package takayuki.techinstitute.jp.memoprot003.Memo;

/**
 * Created by Owner on 2015/12/13.
 */
public class MemoItem {
    private final int id;
    private String title;
    private String memo;

    public String getMemo() {
        return memo;
    }

    public MemoItem(int id, String title, String memo) {
        this.id = id;
        this.memo = memo;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
