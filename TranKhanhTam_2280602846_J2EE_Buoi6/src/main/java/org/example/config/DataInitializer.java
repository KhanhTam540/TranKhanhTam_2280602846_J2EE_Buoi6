package org.example.config;

import org.example.model.Account;
import org.example.model.Role;
import org.example.repository.AccountRepository;
import org.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Tạo roles nếu chưa có
        if (roleRepository.findAll().isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);

            System.out.println("Đã tạo roles mặc định: ADMIN và USER");
        }

        // Tạo tài khoản admin nếu chưa có
        if (accountRepository.findByLoginName("admin").isEmpty()) {
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN không tồn tại"));
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Role USER không tồn tại"));

            Account admin = new Account();
            admin.setLoginName("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);
            admin.setRoles(roles);

            accountRepository.save(admin);
            System.out.println("Đã tạo tài khoản admin");
        }

        // Tạo tài khoản user nếu chưa có
        if (accountRepository.findByLoginName("user1").isEmpty()) {
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Role USER không tồn tại"));

            Account user = new Account();
            user.setLoginName("user1");
            user.setPassword(passwordEncoder.encode("user123"));

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            accountRepository.save(user);
            System.out.println("Đã tạo tài khoản user1");
        }

        System.out.println("Khởi tạo dữ liệu hoàn tất!");
    }
}