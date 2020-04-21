package ru.lukichev.barbershop.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lukichev.barbershop.entities.WorkDay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkWeekDto implements Serializable {
    private Long id;
    private String start;
    private String end;
    private List<WorkDayDto> days = new ArrayList<>();

    public String getRange() {
        return start + " - " + end;
    }
}
