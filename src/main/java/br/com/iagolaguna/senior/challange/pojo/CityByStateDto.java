package br.com.iagolaguna.senior.challange.pojo;


public class CityByStateDto {
    private String uf;
    private Long quantity;

    public CityByStateDto(String uf, Long quantity) {
        this.uf = uf;
        this.quantity = quantity;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
