package com.example.app.domain;

import lombok.Data;

@Data
public class Word {
    private Long id;
    private String english;
    private String correctJapanese;
    private String Japanese;
    private String wrong1;
    private String wrong2;
    private String wrong3;
}
