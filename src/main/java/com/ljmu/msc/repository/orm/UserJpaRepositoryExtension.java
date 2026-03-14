package com.ljmu.msc.repository.orm;

import com.ljmu.msc.domain.User;
import com.ljmu.msc.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepositoryExtension extends JpaRepository<UserEntity, Long> {
}
