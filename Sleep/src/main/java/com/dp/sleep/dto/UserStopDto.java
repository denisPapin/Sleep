package com.dp.sleep.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserStopDto {
    Long userId;
    Long inputDataId;
    Long convertedDataId;
    Long consumableEquipmentId;

}
