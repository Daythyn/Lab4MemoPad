package com.example.lab4memopaddcp;

public class Memo {

    private String memo = "";
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Memo(String memo){
        this.memo = memo;
    }
    public Memo(String memo, int id){
        this.memo = memo;
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
