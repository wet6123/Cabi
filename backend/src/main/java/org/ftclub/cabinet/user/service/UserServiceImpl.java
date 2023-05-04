package org.ftclub.cabinet.user.service;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.ftclub.cabinet.user.domain.User;
import org.ftclub.cabinet.user.domain.UserRole;
import org.ftclub.cabinet.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(String name, String email, Date blackholedAt, UserRole role) {
        Validate.notNull(name, "name must not be null");
        Validate.notNull(role, "role must not be null");
        if (role == UserRole.USER) {
            Validate.notNull(email, "email must not be null");
        }
        User user = new User(name, email, blackholedAt, role);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        Validate.notNull(userId, "userId must not be null");
        User user = userRepository.getUser(userId);
        userRepository.delete(user);
    }

    @Override
    public void updateUserBlackholedAtById(long userId, Date newBlackholedAt) {
        Validate.notNull(userId, "userId must not be null");
        Validate.notNull(newBlackholedAt, "newBlackholedAt must not be null");
        User user = userRepository.getUser(userId);
        user.changeBlackholedAt(newBlackholedAt);
        userRepository.save(user);
    }
}
