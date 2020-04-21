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
@Table(name = "work_day")
public class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String day;

    @ManyToOne
    @JoinColumn(name = "week_id")
    private WorkWeek week;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
    private List<WorkHour> hours = new ArrayList<>();

}
