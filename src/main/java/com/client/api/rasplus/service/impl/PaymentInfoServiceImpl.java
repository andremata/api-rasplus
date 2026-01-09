package com.client.api.rasplus.service.impl;

import com.client.api.rasplus.dto.PaymentProcessDTO;
import com.client.api.rasplus.dto.wsraspay.CustomerDTO;
import com.client.api.rasplus.dto.wsraspay.OrderDTO;
import com.client.api.rasplus.dto.wsraspay.PaymentDTO;
import com.client.api.rasplus.enums.UserTypeEnum;
import com.client.api.rasplus.exception.BusinessException;
import com.client.api.rasplus.exception.NotFoundException;
import com.client.api.rasplus.integration.MailIntegration;
import com.client.api.rasplus.integration.WsRaspayIntegration;
import com.client.api.rasplus.mapper.UserPaymentInfoMapper;
import com.client.api.rasplus.mapper.wsraspay.CreditCardMapper;
import com.client.api.rasplus.mapper.wsraspay.CustomerMapper;
import com.client.api.rasplus.mapper.wsraspay.OrderMapper;
import com.client.api.rasplus.mapper.wsraspay.PaymentMapper;
import com.client.api.rasplus.model.User;
import com.client.api.rasplus.model.UserCredentials;
import com.client.api.rasplus.model.UserPaymentInfo;
import com.client.api.rasplus.model.UserType;
import com.client.api.rasplus.repository.*;
import com.client.api.rasplus.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Value("${ws.rasplus.default.password}")
    private String defaultPassword;

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;
    private final MailIntegration mailIntegration;
    private final UserDetailRepository userDetailRepository;
    private final UserTypeRepository userTypeRepository;
    private final SubscriptionsTypeRepository subscriptionsTypeRepository;

    public PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository,
                                  WsRaspayIntegration wsRaspayIntegration, MailIntegration mailIntegration,
                                  UserDetailRepository userDetailRepository, UserTypeRepository userTypeRepository,
                                  SubscriptionsTypeRepository subscriptionsTypeRepository) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
        this.userDetailRepository = userDetailRepository;
        this.userTypeRepository = userTypeRepository;
        this.subscriptionsTypeRepository = subscriptionsTypeRepository;
    }

    @Override
    public Boolean process(PaymentProcessDTO dto) {
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDTO().getUserId());

        if(userOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado!");
        }

        User user = userOpt.get();

        if(Objects.nonNull(user.getSubscriptionsType())) {
            throw new BusinessException("Pagamento não autorizado, usuário já possui uma assinatura ativa!");
        }

        PaymentDTO paymentDTO = getSucessPayment(dto, user);

        if (createUserCredential(dto, paymentDTO, user)) return true;

        return false;
    }

    private Boolean createUserCredential(PaymentProcessDTO dto, PaymentDTO paymentDTO, User user) {
        if(wsRaspayIntegration.processPayment(paymentDTO)) {
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDTO(), user);
            userPaymentInfoRepository.save(userPaymentInfo);

            var userTypeOpt = userTypeRepository.findById(UserTypeEnum.ALUNO.getId());

            if(userTypeOpt.isEmpty()) {
                throw new NotFoundException("Tipo usuário não encontrado!");
            }

            UserCredentials userCredentials = new UserCredentials(null, user.getEmail(),
                    new BCryptPasswordEncoder().encode(defaultPassword), userTypeOpt.get());

            userDetailRepository.save(userCredentials);

            var subscriptionTypeOpt = subscriptionsTypeRepository.findByProductKey(dto.getProductKey());

            if(subscriptionTypeOpt.isEmpty()) {
                throw new NotFoundException("Tipo de inscrição não encontrada!");
            }

            user.setSubscriptionsType(subscriptionTypeOpt.get());
            userRepository.save(user);

            mailIntegration.send(user.getEmail(), "Segue seu login e senha.", "Acesso Liberado!");

            return true;
        }

        return false;
    }

    private PaymentDTO getSucessPayment(PaymentProcessDTO dto, User user) {
        CustomerDTO customerDTO = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        OrderDTO orderDTO = wsRaspayIntegration.createOrder(OrderMapper.build(customerDTO.getId(), dto));

        PaymentDTO paymentDTO = PaymentMapper.build(customerDTO.getId(), orderDTO.getId(),
                CreditCardMapper.build(dto.getUserPaymentInfoDTO(), user.getCpf()));

        return paymentDTO;
    }
}
