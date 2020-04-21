package ru.lukichev.barbershop.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_hour")
public class WorkHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String time;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private WorkDay day;

    private Long clientId;

    private Long masterId;

    private int status;
}
