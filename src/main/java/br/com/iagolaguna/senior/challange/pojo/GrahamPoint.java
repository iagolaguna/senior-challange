package br.com.iagolaguna.senior.challange.pojo;

public  class GrahamPoint {

    String name;
    double x; // longitude
    double y; // latitude

    public GrahamPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}