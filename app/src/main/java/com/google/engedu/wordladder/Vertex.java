package com.google.engedu.wordladder;

import java.util.ArrayList;

public class Vertex {
    private String name;
    private ArrayList<String> neigbours;

    public String getName() {
        return name;
    }

    public ArrayList<String> getNeigbours() {
        return neigbours;
    }

    public Vertex(String name) {
        this.name = name;
        this.neigbours=new ArrayList<>();

    }
}
