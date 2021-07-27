package sample.Action_club_country_player;

import java.io.Serializable;

public class Player implements Serializable {
    private String Name, Country, Club, Position,img;
    private int Age, Number;
    private double WeeklySalary, Height,sell_price;
    public  boolean sold=true;


    public Player(String name, String country, int age, double height, String club, String position, int number, double weeklySalary, String img,Double sell_price) {
        Name = name;
        Country = country;
        Club = club;
        Position = position;
        Age = age;
        Number = number;
        WeeklySalary = weeklySalary;
        Height = height;
        this.img = img;
        this.sell_price=sell_price;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public double getSell_price() {
        return sell_price;
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getClub() {
        return Club;
    }

    public void setClub(String club) {
        Club = club;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public double getWeeklySalary() {
        return WeeklySalary;
    }

    public void setWeeklySalary(double weeklySalary) {
        WeeklySalary = weeklySalary;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Player{" +
                "Name='" + Name + '\'' +
                ", Country='" + Country + '\'' +
                ", Club='" + Club + '\'' +
                ", Position='" + Position + '\'' +
                ", img='" + img + '\'' +
                ", Age=" + Age +
                ", Number=" + Number +
                ", WeeklySalary=" + WeeklySalary +
                ", Height=" + Height +
                '}';
    }
}
