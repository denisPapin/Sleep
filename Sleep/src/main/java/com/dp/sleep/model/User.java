package com.dp.sleep.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@SequenceGenerator(name = "default_gen", sequenceName = "users_seq", allocationSize = 1)
public class User extends GenericModel{

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "address")
    private String address;

    @Column(name = "subscription")
    private Boolean subscription;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "stop_time")
    private LocalDateTime stopTime;

    @Column(name = "change_password_token")
    private String changePasswordToken;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "role_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_ROLES"))
    private Role role;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private Set<InputData> inputData;

    @OneToMany(mappedBy = "convertedUserId", fetch = FetchType.EAGER)
    private Set<ConvertedData> convertedData;

    @OneToOne(mappedBy = "equipUserId")
    private ConsumableEquipment consumableEquipment;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", subscription=" + subscription +
                ", role=" + role +
                ", inputData=" + inputData +
                ", convertedData=" + convertedData +
                ", consumableEquipment=" + consumableEquipment +
                '}';
    }
}
