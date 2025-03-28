package com.utd.ti.soa.ebs_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    private String name;
    private String last_name;
    private String email;
    private String phone;
    private String born_date;
    private String direction;
}