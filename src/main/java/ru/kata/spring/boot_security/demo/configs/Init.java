package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Init {
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public Init(RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        Role adminRole = roleService.getRoleByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role("ROLE_ADMIN");
            roleService.save(adminRole);
        }
        Role userRole = roleService.getRoleByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleService.save(userRole);


            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(bCryptPasswordEncoder.encode("12345"));
                admin.setName("Вардан");
                admin.setLastName("Хихикян");
                admin.setAge(35);
                admin.setAddress("Лондон");
                admin.setEnabled(true);
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(bCryptPasswordEncoder.encode("1234u"));
                user.setName("Наташя");
                user.setLastName("Кривокрылова");
                user.setAge(18);
                user.setAddress("Тверь");
                user.setEnabled(true);
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }
            if (userRepository.findByUsername("ivan").isEmpty()) {
                User adminIvan = new User();
                adminIvan.setUsername("ivan");
                adminIvan.setPassword(bCryptPasswordEncoder.encode("1111"));
                adminIvan.setName("Иван");
                adminIvan.setLastName("Пшыченко");
                adminIvan.setAge(23);
                adminIvan.setAddress("Москва");
                adminIvan.setEnabled(true);
                adminIvan.setRoles(Set.of(adminRole));
                userRepository.save(adminIvan);
            }
        }


    }
}


