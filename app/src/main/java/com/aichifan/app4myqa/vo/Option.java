package com.aichifan.app4myqa.vo;

public class Option {
    private Integer id;
    private String text;
    
    public Option() {
        
    }
    
    public Option(Integer id, String text) {
        this.id = id;
        this.text = text;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
}
