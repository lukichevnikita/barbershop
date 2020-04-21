package ru.lukichev.barbershop.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  Role implements GrantedAuthority {
    GUEST(0, "Гость"),
    USER(1, "Пользователь"),
    ADMIN(2, "Админ"),
    MASTER(3, "Мастер");

    private static final Map<Integer, Role> ROLE_MAP = new HashMap<>() {{
        for(Role role : Role.values()) {
            put(role.getValue(), role);
        }
    }};

    private int value;
    private String description;

    @Override
    public String getAuthority() {
        return name();
    }
}
