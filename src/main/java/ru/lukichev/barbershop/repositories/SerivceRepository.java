package ru.lukichev.barbershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lukichev.barbershop.entities.ServiceEntity;

public interface SerivceRepository extends JpaRepository<ServiceEntity, Long> {
}
