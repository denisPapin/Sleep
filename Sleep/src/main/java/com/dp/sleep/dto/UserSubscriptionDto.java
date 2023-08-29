package com.dp.sleep.dto;

import lombok.Data;

@Data
public class UserSubscriptionDto {
    Long userId;
    Boolean subscription = true;
}
