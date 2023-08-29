package com.dp.sleep.service;

import com.dp.sleep.dto.ConsumableEquipmentDto;
import com.dp.sleep.mapper.ConsumableEquipmentMapper;
import com.dp.sleep.model.ConsumableEquipment;
import com.dp.sleep.repository.ConsumableEquipmentRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsumableEquipmentService extends GenericService<ConsumableEquipment>{

    private final ConsumableEquipmentRepository repository;

    private final ConsumableEquipmentMapper mapper;

    protected ConsumableEquipmentService(ConsumableEquipmentRepository repository, UserService userService, ConsumableEquipmentMapper mapper) {
        super(repository);
        this.repository = repository;

        this.mapper = mapper;
    }

    public ConsumableEquipment findByUserId(Long userId){
        return repository.findConsumableEquipment(userId);
    }


}
