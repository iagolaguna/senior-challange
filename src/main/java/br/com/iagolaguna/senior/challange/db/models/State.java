package br.com.iagolaguna.senior.challange.db.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class State {

    @Id
    private String uf;

    public State() {
    }

    public State(String uf) {
        this.uf = uf;
    }


    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
