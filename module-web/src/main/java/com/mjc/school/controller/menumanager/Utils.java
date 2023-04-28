package com.mjc.school.controller.menumanager;

public enum Utils {
    ENTER_AUTHOR_ID("Enter Author ID: "),
    ENTER_AUTHOR_NAME("Enter Author NAME: "),
    ENTER_NEWS_ID("Enter News ID: "),
    ENTER_NEWS_TITLE("Enter News TITLE: "),
    ENTER_NEWS_CONTENT("Enter News CONTENT: "),
    ENTER_AUTHOR_ID_NEWS("Enter Author ID: "),
    ENTER_TAG_ID("Enter Tag ID: "),
    ENTER_TAG_NAME("Enter Tag name: "),
    ENTER_TAG_IDS("Enter Tag Ids: "),
    ENTER_TAG_NAMES("Enter Tag names: ");

    private final String content;
    Utils(String content) {
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }
}