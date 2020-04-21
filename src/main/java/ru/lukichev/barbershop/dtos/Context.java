package ru.lukichev.barbershop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Context implements Serializable {
    private Long id;
    private boolean authorized;
    private boolean admin;
    private boolean master;
    private String user;
    private String name;
    private String phoneNumber;
}
