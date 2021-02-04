package be.gerard.robot.model;

import lombok.Value;

import java.util.List;

@Value
public class MediaList {
    String id;
    List<Media> media;

    @Value
    public static class Media {
        String d;
        List<Item> fs;
    }

    @Value
    public static class Item {
        String n;
        String mod;
        String glrv;
        String s;
        String ls;
    }
}
