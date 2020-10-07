package com.example.attendancetracker;

public class TeacherUser  {
    public String name, email, department, tele;

    public TeacherUser(){

    }
    public TeacherUser(String name, String email, String department, String tele) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.tele=tele;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }
}
