package com.hh.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum implements CodeEnum {
    WAIT(0,"等待状态"),
    SUCCESS(1,"支付成功");

    private Integer code;
    private String message;
}
