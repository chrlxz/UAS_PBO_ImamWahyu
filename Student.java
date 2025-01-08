package com.example;

public class Student {
    private int no;
    private int id;
    private String nama;
    private String nim;
    private String fakultas;
    private String noTelepon;

    public Student(int no, int id, String nama, String nim, String fakultas, String noTelepon) {
        this.no = no;
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.fakultas = fakultas;
        this.noTelepon = noTelepon;
    }

    public int getNo() { return no; }
    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getNim() { return nim; }
    public String getFakultas() { return fakultas; }
    public String getNoTelepon() { return noTelepon; }
}
