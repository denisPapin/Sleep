package com.dp.sleep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InputDataAverageDto extends GenericDto{
    private Double averageTemperature;
    private Double averageHumidity;
    private Long userId;
}
