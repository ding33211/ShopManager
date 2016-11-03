package com.soubu.goldensteward.module.server;

/**
 * Created by lakers on 16/11/2.
 */

public class MainProductTagServerParams {
    int id;
    String name;
    Level[] next_level;


    public class Level {
        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level[] getNext_level() {
        return next_level;
    }

    public void setNext_level(Level[] next_level) {
        this.next_level = next_level;
    }
}
