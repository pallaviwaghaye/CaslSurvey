package com.casl.model;

/**
 * Created by kang on 7/14/2017.
 */

public class Interview{
    private int id;
    private String name;
    public Interview(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Interview() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String toString(){
        return this.name;
    }

}
