package com.dp.sleep.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TimeDto {
    private LocalDateTime start;
    private LocalDateTime stop;
    private Long userId;
}
