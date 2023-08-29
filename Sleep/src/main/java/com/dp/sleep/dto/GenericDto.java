package com.dp.sleep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class GenericDto {

  protected Long id;
  protected String createdBy = "DEFAULT_USER";
  protected boolean isDeleted = false;
  protected String deletedBy;
  protected String updatedBy;
}
