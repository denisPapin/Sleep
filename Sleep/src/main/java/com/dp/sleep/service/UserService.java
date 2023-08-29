package com.dp.sleep.service;

import com.dp.sleep.constants.MailConstants;
import com.dp.sleep.dto.*;
import com.dp.sleep.mapper.ConsumableEquipmentMapper;
import com.dp.sleep.model.User;
import com.dp.sleep.repository.ConsumableEquipmentRepository;
import com.dp.sleep.repository.UserRepository;
import com.dp.sleep.utils.MailUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.dp.sleep.constants.UserRolesConstants.USER;

@Service
public class UserService extends GenericService<User>{

    private final InputDataService inputDataService;
    private final ConvertedDataService convertedDataService;
    private final UserRepository repository;
    private final RoleService roleService;
    private final JavaMailSender javaMailSender;


    private final ConsumableEquipmentMapper consumableEquipmentMapper;
    private final ConsumableEquipmentRepository consumableEquipmentRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    protected UserService(UserRepository repository, InputDataService inputDataService,
                          ConvertedDataService convertedDataService,
                          RoleService roleService,
                          JavaMailSender javaMailSender,
                          ConsumableEquipmentMapper consumableEquipmentMapper,
                          ConsumableEquipmentRepository consumableEquipmentRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository);
        this.inputDataService = inputDataService;
        this.convertedDataService = convertedDataService;
        this.repository = repository;
        this.roleService = roleService;
        this.javaMailSender = javaMailSender;

        this.consumableEquipmentMapper = consumableEquipmentMapper;
        this.consumableEquipmentRepository = consumableEquipmentRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean checkPassword(String password, UserDetails foundUser) {
        return bCryptPasswordEncoder.matches(password, foundUser.getPassword());
    }

    public User getUserByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    public User getUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }





    @Override
    public User create(User object) {
        object.setRole(roleService.getByTitle(USER));
        object.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setCreatedWhen(LocalDateTime.now());
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return super.create(object);
    }

    @Override
    public User update(User object) {
        User foundUser = getOne(object.getId());
        object.setRole(foundUser.getRole());
        object.setPassword(foundUser.getPassword());
        object.setDeleted(foundUser.isDeleted());
        object.setDeletedWhen(foundUser.getDeletedWhen());
        object.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setUpdatedWhen(LocalDateTime.now());
        object.setCreatedBy(foundUser.getCreatedBy());
        object.setCreatedWhen(foundUser.getCreatedWhen());
        return super.update(object);
    }

    public void sendChangePasswordEmail(User user) {
        UUID uuid = UUID.randomUUID();
        user.setChangePasswordToken(uuid.toString());
        update(user);
        SimpleMailMessage message = MailUtils.createEmailMessage(
                user.getEmail(),
                MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD
        );
        javaMailSender.send(message);
    }

    public void changePassword(String uuid, String password) {
        User user = repository.findUserByChangePasswordToken(uuid);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setChangePasswordToken(null);
        update(user);
    }

    public void setSub(UserSubscriptionDto userSubscriptionDto) {
    User user = getOne(userSubscriptionDto.getUserId());
    user.setSubscription(true);
    user.setRole(roleService.getByTitle("SUPER"));
    update(user);
    }

    public void setStartTime(UserTimeDto userTimeDto) {
        User user = getOne(userTimeDto.getUserId());
        user.setStartTime(LocalDateTime.now());
        update(user);
    }

    public void stop(UserStopDto userStopDto){
        User user = getOne(userStopDto.getUserId());
        user.setStopTime(LocalDateTime.now());
        TimeDto timeDto = new TimeDto();
        timeDto.setStart(user.getStartTime());
        timeDto.setStop(user.getStopTime());
        timeDto.setUserId(userStopDto.getUserId());
        convertedDataService.convertData(inputDataService.averageInputDataValues(timeDto), timeDto);
        useEquipment((repository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName())).getId());
    }

    public void useEquipment(Long userId){
        if(consumableEquipmentRepository.findConsumableEquipment(userId) != null){
            ConsumableEquipmentDto consumableEquipmentDto = consumableEquipmentMapper
                    .toDto(consumableEquipmentRepository.findConsumableEquipment(userId));
            consumableEquipmentDto.setRemainingWorkingTime(consumableEquipmentDto.getRemainingWorkingTime() - 1L);
            if(consumableEquipmentDto.getRemainingWorkingTime() < 3L)
                consumableEquipmentDto.setReplacementRequired(true);
            consumableEquipmentRepository.save(consumableEquipmentMapper.toEntity(consumableEquipmentDto));
        } else {
            ConsumableEquipmentDto consumableEquipmentDto = new ConsumableEquipmentDto();
            consumableEquipmentDto.setEquipUserId(userId);
            consumableEquipmentDto.setWorkResource("30 дней");
            consumableEquipmentDto.setRemainingWorkingTime(30L);
            consumableEquipmentRepository.save(consumableEquipmentMapper.toEntity(consumableEquipmentDto));
            getOne(userId).setConsumableEquipment(consumableEquipmentRepository.findConsumableEquipment(userId));

        }

    }
}
