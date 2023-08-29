package com.dp.sleep.dto;

import com.dp.sleep.model.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MeditationSearchDto {
    private String name;
    private Direction direction;
}
