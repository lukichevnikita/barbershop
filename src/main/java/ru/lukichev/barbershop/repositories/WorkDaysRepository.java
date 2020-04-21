package ru.lukichev.barbershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lukichev.barbershop.entities.WorkDay;

import java.util.List;

@Repository
public interface WorkDaysRepository extends JpaRepository<WorkDay, Long> {

    @Query(" select w from WorkDay d " +
           " join WorkWeek w on d.week.id = w.id " +
           " where w.startWeek = :startWeek and d.day = :day ")
    List<WorkDay> findAllByStartWeek(@Param("startWeek") String startWeek, @Param("day") String day);
}
