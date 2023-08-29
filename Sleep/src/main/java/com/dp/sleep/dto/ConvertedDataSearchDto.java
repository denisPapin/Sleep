package com.dp.sleep.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ConvertedDataSearchDto {
    private Long sleepDuration;
    private String sleepQuality;
    private LocalDate date;
}
