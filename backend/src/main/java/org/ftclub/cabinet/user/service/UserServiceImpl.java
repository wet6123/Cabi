package org.ftclub.cabinet.user.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.ftclub.cabinet.cabinet.domain.LentType;
import org.ftclub.cabinet.lent.repository.LentRepository;
import org.ftclub.cabinet.user.domain.AdminRole;
import org.ftclub.cabinet.user.domain.AdminUser;
import org.ftclub.cabinet.user.domain.BanHistory;
import org.ftclub.cabinet.user.domain.BanPolicy;
import org.ftclub.cabinet.user.domain.BanType;
import org.ftclub.cabinet.user.domain.User;
import org.ftclub.cabinet.user.domain.UserRole;
import org.ftclub.cabinet.user.repository.AdminUserRepository;
import org.ftclub.cabinet.user.repository.BanHistoryRepository;
import org.ftclub.cabinet.user.repository.UserRepository;
import org.ftclub.cabinet.utils.DateUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AdminUserRepository adminUserRepository;
    private final BanHistoryRepository banHistoryRepository;
    private final BanPolicy banPolicy;
    private final LentRepository lentRepository;

    @Override
    public boolean checkUserExists(String name) {
        Optional<User> user = userRepository.findByName(name);
        return user.isPresent();
    }

    @Override
    public void createUser(String name, String email, Date blackholedAt, UserRole role) {
        if (role == UserRole.USER) {
            Validate.notNull(email, "email must not be null");
        }
        if (!checkUserExists(name)) {
            User user = User.of(name, email, blackholedAt, role);
            userRepository.save(user);
        }
    }

    @Override
    public boolean checkAmdinUserExists(String name) {
        Optional<AdminUser> adminUser = adminUserRepository.findByEmail(name);
        return adminUser.isPresent();
    }

    @Override
    public void createAdminUser(String email) {
        if (!checkAmdinUserExists(email)) {
            AdminUser adminUser = AdminUser.of(email, AdminRole.NONE);
            adminUserRepository.save(adminUser);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.getUser(userId);
        userRepository.delete(user);
    }

    @Override
    public void deleteAdminUser(Long adminUserId) {
        AdminUser adminUser = adminUserRepository.getAdminUser(adminUserId);
        adminUserRepository.delete(adminUser);
    }

    @Override
    public void updateUserBlackholedAtById(Long userId, Date newBlackholedAt) {
        User user = userRepository.getUser(userId);
        user.changeBlackholedAt(newBlackholedAt);
        userRepository.save(user);
    }

    @Override
    public void updateAdminUserRole(Long adminUserId, AdminRole role) {
        AdminUser adminUser = adminUserRepository.getAdminUser(adminUserId);
        adminUser.changeAdminRole(role);
        adminUserRepository.save(adminUser);
    }

    @Override
    public void banUser(Long userId, LentType lentType, Date startedAt, Date endedAt,
            Date expiredAt) {
        BanType banType = banPolicy.verifyForBanType(lentType, startedAt, endedAt, expiredAt);
        if (banType == BanType.NONE) {
            return;
        }
        Date banDate = banPolicy.getBanDate(banType, endedAt, expiredAt);
        BanHistory banHistory = BanHistory.of(endedAt, DateUtil.addDaysToDate(banDate,
                        getAccumulateOverdueDaysByUserId(userId).intValue()),
                banType, userId);
        banHistoryRepository.save(banHistory);
    }

//    void unbanUser(Long userId);

    @Override
    public Long getAccumulateOverdueDaysByUserId(Long userId) {
        List<BanHistory> banHistories = banHistoryRepository.findBanHistoriesByUserId(userId);
        Long accumulateDays = (long) 0;
        for (BanHistory history : banHistories) {
            accumulateDays += DateUtil.calculateTwoDateDiffAbs(history.getBannedAt(),
                    history.getUnbannedAt());
        }
        return accumulateDays;
    }
}
