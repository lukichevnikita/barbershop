package ru.lukichev.barbershop.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_week")
public class WorkWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "week", cascade = CascadeType.ALL)
    private List<WorkDay> days = new ArrayList<>();

    private String startWeek;
    private String endWeek;
}
