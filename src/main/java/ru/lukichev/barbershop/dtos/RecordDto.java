package ru.lukichev.barbershop.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordDto implements Serializable {
    private Long id;
    private String startWeekDate;
    private String day;
    private String hour;
    private Long master;
    private Long client;
    private int statusId;
}
