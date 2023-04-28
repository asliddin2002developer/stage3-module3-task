package com.mjc.school.service.view.impl;


import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.view.View;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsView implements View<NewsDtoResponse, List<NewsDtoResponse>> {

    public void display(NewsDtoResponse news){
       System.out.println(news);
    }

    public void displayAll(List<NewsDtoResponse> news){
        news.forEach(System.out::println);
    }
}
