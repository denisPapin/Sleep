package com.dp.sleep.dto;

import com.dp.sleep.model.ConsumableEquipment;
import com.dp.sleep.model.ConvertedData;
import com.dp.sleep.model.InputData;
import com.dp.sleep.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends GenericDto{

    private String login;
    private String password;
    private String email;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String country;
    private String address;
    private Boolean subscription;
    private Role role;
    private Set<Long> convertedDataId;
    private Set<Long> inputDataId;
    private ConsumableEquipment consumableEquipment;

}
