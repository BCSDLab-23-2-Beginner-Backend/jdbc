package bcsdbeginner.jdbc.domain;

public class Categories {
    private int id;
    private String name;

    public Categories(String name){
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
