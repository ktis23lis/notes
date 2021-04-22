package com.example.notes;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class NameNotesMapping {

    public  static class Fields{
        public final static String NAME = "name";
        public final static String TEXT = "text";
        public final static String DATE = "date";
    }

    public static NameNotes toNameNotes(String id, Map<String, Object> doc){
        NameNotes answer = new NameNotes((String) doc.get(Fields.NAME),
                (String) doc.get(Fields.TEXT),
                (String) doc.get(Fields.DATE));
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(NameNotes nameNotes){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME, nameNotes.getName());
        answer.put(Fields.TEXT, nameNotes.getText());
        answer.put(Fields.DATE, nameNotes.getDate());
        return answer;
    }

}
