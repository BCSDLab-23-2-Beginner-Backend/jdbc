package bcsdbeginner.jdbc.domain;

public class Hashtags {
    private int id;
    private String name;

    public Hashtags(String name){
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
