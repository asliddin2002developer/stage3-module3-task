package com.mjc.school.controller.menumanager;

import org.springframework.stereotype.Component;

@Component("menu")
public class Menu {
    public static final String READ_ALL_AUTHORS = "1";
    public static final String READ_ALL_NEWS = "2";
    public static final String READ_ALL_TAGS = "3";
    public static final String READ_AUTHOR_BY_ID = "4";
    public static final String READ_NEWS_BY_ID = "5";
    public static final String READ_TAG_BY_ID = "6";
    public static final String CREATE_AUTHOR = "7";
    public static final String CREATE_NEWS = "8";
    public static final String CREATE_TAG = "9";
    public static final String UPDATE_AUTHOR = "10";
    public static final String UPDATE_NEWS = "11";
    public static final String UPDATE_TAG = "12";
    public static final String DELETE_AUTHOR_BY_ID = "13";
    public static final String DELETE_NEWS_BY_ID = "14";
    public static final String DELETE_TAG_BY_ID = "15";
    public static final String FIND_TAGS_BY_NEWS_ID = "16";
    public static final String FIND_AUTHORS_BY_NEWS_ID = "17";
    public static final String FIND_NEWS_BY_PARAMS = "18";
    public static final String EXIT = "0";

    public void display() {
        System.out.println("Welcome to the Main Menu");
        System.out.println("1. Read all authors");
        System.out.println("2. Read all news");
        System.out.println("3. Read all tags");
        System.out.println("4. Read author by ID");
        System.out.println("5. Read news by ID");
        System.out.println("6. Read tag by ID");
        System.out.println("7. Create new author");
        System.out.println("8. Create new news");
        System.out.println("9. Create new tag");
        System.out.println("10. Update author");
        System.out.println("11. Update news");
        System.out.println("12. Update tag");
        System.out.println("13. Delete author by ID");
        System.out.println("14. Delete news by ID");
        System.out.println("15. Delete tag by ID");
        System.out.println("16. Find tags by news ID");
        System.out.println("17. Find author by news ID");
        System.out.println("18. Find news by params");
        System.out.println("0. Exit\n");
        System.out.print("Please enter your choice:  ");
    }
}