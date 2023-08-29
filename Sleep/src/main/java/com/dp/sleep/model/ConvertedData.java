package com.dp.sleep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "converted_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConvertedData extends GenericModel{



    @Column(name = "sleep_duration")
    private Long sleepDuration;

    @Column(name = "sleep_quality")
    private String sleepQuality;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_USER"))
    private User convertedUserId;

    @Column(name = "date")
    private LocalDate date;
}
