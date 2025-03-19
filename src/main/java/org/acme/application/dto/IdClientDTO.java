package org.acme.application.dto;

import lombok.Data;

@Data
public class IdClientDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
}
