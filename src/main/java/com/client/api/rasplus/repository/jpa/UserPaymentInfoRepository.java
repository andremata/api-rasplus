package com.client.api.rasplus.repository.jpa;

import com.client.api.rasplus.model.jpa.UserPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentInfoRepository extends JpaRepository<UserPaymentInfo, Long> {
}
