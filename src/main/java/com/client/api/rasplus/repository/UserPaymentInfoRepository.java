package com.client.api.rasplus.repository;

import com.client.api.rasplus.model.UserPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentInfoRepository extends JpaRepository<UserPaymentInfo, Long> {
}
