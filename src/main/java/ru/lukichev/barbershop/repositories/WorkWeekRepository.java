package ru.lukichev.barbershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lukichev.barbershop.entities.WorkWeek;

import java.util.List;

@Repository
public interface WorkWeekRepository extends JpaRepository<WorkWeek, Long> {
    List<WorkWeek> findAllByStartWeekIn(List<String> starts);

    @Query(value = " SELECT distinct * " +
                   " FROM work_week w " +
                   " JOIN work_day d on w.id = d.week_id " +
                   " JOIN work_hour h on d.id = h.day_id " +
                   " WHERE h.client_id = :clientId ", nativeQuery = true)
    List<WorkWeek> findAllByClientId(@Param("clientId") Long clientId);

    @Query(value = " SELECT distinct * " +
                   " FROM work_week w " +
                   " JOIN work_day d on w.id = d.week_id " +
                   " JOIN work_hour h on d.id = h.day_id " +
                   " WHERE h.master_id = :masterId ", nativeQuery = true)
    List<WorkWeek> findAllByMasterId(@Param("masterId") Long id);
}
