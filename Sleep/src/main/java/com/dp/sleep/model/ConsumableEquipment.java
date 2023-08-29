package com.dp.sleep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "consumable_equipment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsumableEquipment extends GenericModel{

    @Column(name = "work_resource")
    private String workResource;

    @Column(name = "remaining_working_time")
    private Long remainingWorkingTime= 30L;

    @Column(name = "replacement_required")
    private Boolean replacementRequired = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User equipUserId;
}
