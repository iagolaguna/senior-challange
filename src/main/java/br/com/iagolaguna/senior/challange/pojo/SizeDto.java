package br.com.iagolaguna.senior.challange.pojo;

public class SizeDto {

    private int size;


    public SizeDto(Long aLong) {
    }

    public SizeDto(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
