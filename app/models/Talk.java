package models;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class Talk {

    public String title;
    public Date fromTime;
    public Date toTime;
    public String room;
    public String type;
    public String uri;
    public String id;
    public List speakers;

    public Talk(Map<String, Object> talkAsMap) {
        this.fromTime = parseDate(valueOf(talkAsMap.get("fromTime")));
        this.toTime = parseDate(valueOf(talkAsMap.get("toTime")));
        Object tmpTitle = talkAsMap.get("title");
        if(tmpTitle != null) {
            this.title = valueOf(tmpTitle);
        }
        this.room = valueOf(talkAsMap.get("room"));
        this.type = valueOf(talkAsMap.get("type"));
        this.uri = valueOf(talkAsMap.get("presentationUri"));
        this.id = valueOf(talkAsMap.get("id"));
        this.speakers = (List) talkAsMap.get("speakers");
    }

    private Date parseDate(String strDate)  {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
