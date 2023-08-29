package com.dp.sleep.dto;

import com.dp.sleep.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsumableEquipmentDto extends GenericDto{

    private String workResource;
    private Long remainingWorkingTime;
    private Boolean replacementRequired = false;
    private Long equipUserId;
}
