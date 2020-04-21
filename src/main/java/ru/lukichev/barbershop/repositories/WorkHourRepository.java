package ru.lukichev.barbershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lukichev.barbershop.entities.WorkHour;

@Repository
public interface WorkHourRepository extends JpaRepository<WorkHour, Long> {

}
