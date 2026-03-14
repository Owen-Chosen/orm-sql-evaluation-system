package com.ljmu.msc.repository.orm;

import com.ljmu.msc.domain.User;
import com.ljmu.msc.domain.entities.UserEntity;
import com.ljmu.msc.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("orm")
public class UserRepositoryOrm implements UserRepository {

    private final UserJpaRepositoryExtension userJpaRepositoryExtension;

    public UserRepositoryOrm(UserJpaRepositoryExtension userJpaRepositoryExtension) {
        this.userJpaRepositoryExtension = userJpaRepositoryExtension;
    }

    @Override
    public User save(User user) {
        return userJpaRepositoryExtension.save(UserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public Optional<User> findById(Long id) {
        UserEntity userEntity = userJpaRepositoryExtension.findById(id).orElseThrow();
        return Optional.of(userEntity.toDomain());
    }

    @Override
    public List<User> findAll() {
        return userJpaRepositoryExtension.findAll()
                .stream()
                .map(UserEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepositoryExtension.deleteById(id);
    }
}
