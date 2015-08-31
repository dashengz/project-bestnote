package edu.columbia.jonathan.project_bestnote;

/**
 * Created by Jonathan on 4/20/15.
 */
public class NoteObjects {
    public String title;
    public String createTime;
    public String content;
    public String objectId;

    public NoteObjects(String t, String d, String o, String i) {
        title = t;
        createTime = d;
        content = o;
        objectId = i;
    }
}
