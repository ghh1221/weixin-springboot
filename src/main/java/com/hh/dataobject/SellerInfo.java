package com.hh.dataobject;

import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SellerInfo {
    @Id
    private String sellerId;
    private String username;
    private String password;
    private String openid;
}
