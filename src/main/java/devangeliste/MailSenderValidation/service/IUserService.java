package devangeliste.MailSenderValidation.service;

import devangeliste.MailSenderValidation.model.entity.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> findAll();
}
