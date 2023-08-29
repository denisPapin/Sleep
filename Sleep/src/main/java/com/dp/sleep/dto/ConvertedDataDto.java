package com.dp.sleep.dto;

import com.dp.sleep.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConvertedDataDto extends GenericDto{

    private Long sleepDuration;
    private String sleepQuality;
    private Long convertedUserId;
    private LocalDate date;
}
