package com.dp.sleep.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meditations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Meditation extends GenericModel{

    @Column(name = "name")
    private String name;

    @Column(name = "direction", nullable = false)
    @Enumerated
    private Direction direction;

    @Column(name = "file_name")
    private String fileName;
}
