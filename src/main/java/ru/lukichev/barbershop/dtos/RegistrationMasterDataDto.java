package ru.lukichev.barbershop.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationMasterDataDto implements Serializable {
    private RegistrationDataDto registrationData;
    private String name;
    private String functions;
    private String photo;
}
