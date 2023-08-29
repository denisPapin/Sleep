package com.dp.sleep.mapper;

import com.dp.sleep.dto.ConsumableEquipmentDto;
import com.dp.sleep.model.ConsumableEquipment;
import com.dp.sleep.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
@Component
public class ConsumableEquipmentMapper extends GenericMapper<ConsumableEquipment, ConsumableEquipmentDto>{


    private final UserRepository userRepository;


    protected ConsumableEquipmentMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper, ConsumableEquipment.class, ConsumableEquipmentDto.class);
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        super.modelMapper.createTypeMap(ConsumableEquipment.class, ConsumableEquipmentDto.class)
                .addMappings(m -> m.skip(ConsumableEquipmentDto::setEquipUserId)).setPostConverter(toDtoConverter());


        super.modelMapper.createTypeMap(ConsumableEquipmentDto.class, ConsumableEquipment.class)
                .addMappings(m -> m.skip(ConsumableEquipment::setEquipUserId)).setPostConverter(toEntityConverter());

    }

    @Override
    protected void mapSpecificFields(ConsumableEquipmentDto source, ConsumableEquipment destination) {
        destination.setEquipUserId(userRepository.findById(source.getEquipUserId()).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    protected void mapSpecificFields(ConsumableEquipment source, ConsumableEquipmentDto destination) {
        destination.setEquipUserId(source.getEquipUserId().getId());
    }
}
