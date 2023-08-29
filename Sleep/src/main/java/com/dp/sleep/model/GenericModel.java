package com.dp.sleep.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class GenericModel {

        @Id
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
        protected Long id;

        @Column(name = "created_when")
        protected LocalDateTime createdWhen = LocalDateTime.now();

        @Column(name = "created_by")
        protected String createdBy = "DEFAULT_USER";

        @Column(name = "is_deleted")
        protected boolean isDeleted = false;

        @Column(name = "deleted_by")
        protected String deletedBy;

        @Column(name = "deleted_when")
        protected LocalDateTime deletedWhen;

        @Column(name = "updated_when")
        protected LocalDateTime updatedWhen;

        @Column(name = "updated_by")
        protected String updatedBy;

}
