package com.dp.sleep.dto;

import com.dp.sleep.model.Direction;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeditationDto extends GenericDto{


    private String name;
    private Direction direction;
    private String fileName;

}
