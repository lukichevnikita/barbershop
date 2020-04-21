package ru.lukichev.barbershop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lukichev.barbershop.dtos.Context;
import ru.lukichev.barbershop.dtos.MasterDto;
import ru.lukichev.barbershop.dtos.RegistrationDataDto;
import ru.lukichev.barbershop.dtos.RegistrationMasterDataDto;
import ru.lukichev.barbershop.entities.Master;
import ru.lukichev.barbershop.entities.Role;
import ru.lukichev.barbershop.entities.User;
import ru.lukichev.barbershop.entities.UserInfo;
import ru.lukichev.barbershop.repositories.MasterRepository;
import ru.lukichev.barbershop.repositories.UserInfoRepository;
import ru.lukichev.barbershop.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final MasterRepository masterRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    public void createUser(RegistrationDataDto registrationData) {
        long count = userRepository.count();

        User user = new User();
        user.setUsername(registrationData.getUsername());
        user.setPassword(passwordEncoder.encode(registrationData.getPassword()));
        user.setActive(true);
        user.getRoles().add(Role.USER);
        if (count == 0) {
            user.getRoles().add(Role.ADMIN);
        }

        userRepository.save(user);

        userInfoRepository.save(new UserInfo(user.getId()));
    }

    public void createMaster(RegistrationMasterDataDto data) {
        createUser(data.getRegistrationData());
        User user = userRepository.findByUsername(data.getRegistrationData().getUsername());

        UserInfo userInfo = userInfoRepository.findByUserId(user.getId());
        userInfo.setName(data.getName());
        userInfoRepository.save(userInfo);

        Master master = Master.builder()
                .photo(data.getPhoto())
                .userId(user.getId())
                .functions(data.getFunctions())
                .build();

        masterRepository.save(master);
    }

    public Context buildContext(User user) {
        UserInfo userInfo = getUserInfoByUserId(user.getId());

        return Context.builder()
                .id(user.getId())
                .admin(user.getRoles().contains(Role.ADMIN))
                .authorized(true)
                .master(user.getRoles().contains(Role.MASTER))
                .user(user.getUsername())
                .name(userInfo.getName())
                .phoneNumber(userInfo.getPhoneNumber())
                .build();
    }

    private UserInfo getUserInfoByUserId(Long userId) {
        return userInfoRepository.findByUserId(userId);
    }

    public void changeName(User user, String name) {
        UserInfo userInfo = getUserInfoByUserId(user.getId());
        userInfo.setName(name);

        userInfoRepository.save(userInfo);
    }

    public void changeUsername(User user, String username) {
        user.setUsername(username);

        userRepository.save(user);
    }

    public void changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    public void changePhoneNumber(User user, String phoneNumber) {
        UserInfo userInfo = getUserInfoByUserId(user.getId());
        userInfo.setPhoneNumber(phoneNumber);

        userInfoRepository.save(userInfo);
    }

    public List<MasterDto> getMasters() {
        return masterRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private MasterDto mapToDto(Master master) {
        UserInfo userInfo = userInfoRepository.findByUserId(master.getUserId());

        return MasterDto.builder()
                .id(master.getUserId())
                .name(userInfo.getName())
                .functions(master.getFunctions())
                .photo(master.getPhoto())
                .build();
    }

    public void changePhoto(User user, String changePhoto) {
        Master byUserId = masterRepository.findByUserId(user.getId());
        byUserId.setPhoto(changePhoto);
        masterRepository.save(byUserId);
    }

    public void changeFunctions(User user, String changeFunctions) {
        Master byUserId = masterRepository.findByUserId(user.getId());
        byUserId.setFunctions(changeFunctions);
        masterRepository.save(byUserId);
    }

    public String getFunctions(User user) {
        Master byUserId = masterRepository.findByUserId(user.getId());
        return byUserId.getFunctions();
    }
}
