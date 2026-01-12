package com.client.api.rasplus.repository.jpa;

import com.client.api.rasplus.model.jpa.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository  extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> findByUsername(String username);
}
