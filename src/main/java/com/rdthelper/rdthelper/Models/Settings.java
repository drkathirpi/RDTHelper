package com.rdthelper.rdthelper.Models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "rdtToken", nullable = false)
    private String rdtToken;
    @Column(name = "showAll", nullable = false)
    private Boolean showAll;


    public Settings(){
    }

    public Settings(String rdtToken, Boolean showAll){
        this.rdtToken = rdtToken;
        this.showAll = showAll;
    }

    public Settings(Long id, String rdtToken, Boolean showAll){
        this.id = id;
        this.rdtToken = rdtToken;
        this.showAll = showAll;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRdtToken() {
        return rdtToken;
    }

    public void setRdtToken(String rdtToken) {
        this.rdtToken = rdtToken;
    }

    public Boolean getShowAll() {
        return showAll;
    }

    public void setShowAll(Boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "id=" + id +
                ", rdtToken='" + rdtToken + '\'' +
                ", showAll=" + showAll +
                '}';
    }
}
