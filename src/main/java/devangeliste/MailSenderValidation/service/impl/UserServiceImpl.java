package devangeliste.MailSenderValidation.service.impl;

import devangeliste.MailSenderValidation.model.entity.UserEntity;
import devangeliste.MailSenderValidation.repository.UserRepository;
import devangeliste.MailSenderValidation.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
