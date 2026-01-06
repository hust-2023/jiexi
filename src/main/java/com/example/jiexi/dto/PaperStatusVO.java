package com.example.jiexi.dto;

import lombok.Data;

@Data
public class PaperStatusVO {
    private Long paperId;
    private String paperName;
    private String parseStatus; // WAITING / PARSING / PARSED / ERROR
}
