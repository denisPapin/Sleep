package com.dp.sleep.mapper;

import com.dp.sleep.dto.UserDto;
import com.dp.sleep.model.ConvertedData;
import com.dp.sleep.model.InputData;
import com.dp.sleep.model.User;
import com.dp.sleep.repository.ConsumableEquipmentRepository;
import com.dp.sleep.repository.ConvertedDataRepository;
import com.dp.sleep.model.GenericModel;
import com.dp.sleep.repository.InputDataRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper extends GenericMapper<User, UserDto> {

    private final ConvertedDataRepository convertedDataRepository;
    private final InputDataRepository inputDataRepository;
    private final ConsumableEquipmentRepository consumableEquipmentRepository;



    protected UserMapper(ModelMapper modelMapper, ConvertedDataRepository convertedDataRepository, InputDataRepository inputDataRepository, ConsumableEquipmentRepository consumableEquipmentRepository) {
        super(modelMapper, User.class, UserDto.class);

        this.convertedDataRepository = convertedDataRepository;
        this.inputDataRepository = inputDataRepository;
        this.consumableEquipmentRepository = consumableEquipmentRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setConvertedDataId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(UserDto::setInputDataId)).setPostConverter(toDtoConverter());
//                .addMappings(m -> m.skip(UserDto::setConsumableEquipmentId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setConvertedData)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(User::setInputData)).setPostConverter(toEntityConverter());
//                .addMappings(m -> m.skip(User::setConsumableEquipment)).setPostConverter(toEntityConverter());


    }

    @Override
    protected void mapSpecificFields(UserDto source, User destination) {
        if (!Objects.isNull(source.getConvertedDataId())) {
            destination.setConvertedData(new HashSet<>(convertedDataRepository.findAllById(source.getConvertedDataId())));
           destination.setInputData(new HashSet<>(inputDataRepository.findAllById(source.getInputDataId())));
//           destination.setConsumableEquipment(consumableEquipmentRepository.findConsumableEquipmentById(source.getConsumableEquipmentId()));
        }
        else {
            destination.setConvertedData(Collections.emptySet());
            destination.setInputData(Collections.emptySet());
//            destination.setConsumableEquipment(null);
        }
    }

    @Override
    protected void mapSpecificFields(User source, UserDto destination) {
        destination.setConvertedDataId(getIds1(source));
        destination.setInputDataId(getIds2(source));
//        destination.setConsumableEquipmentId(getIds(source));
    }

    protected Set<Long> getIds1(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getConvertedData())
                ? null
                : entity.getConvertedData().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }

    protected Set<Long> getIds2(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getInputData())
                ? null
                : entity.getInputData().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
