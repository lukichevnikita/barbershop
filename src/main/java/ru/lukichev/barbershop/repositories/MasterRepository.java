package ru.lukichev.barbershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lukichev.barbershop.entities.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    Master findByUserId(Long userId);
}
