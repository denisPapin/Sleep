package com.dp.sleep.mapper;

import com.dp.sleep.dto.InputDataDto;
import com.dp.sleep.model.InputData;
import com.dp.sleep.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.webjars.NotFoundException;

public class InputDataMapper extends GenericMapper<InputData, InputDataDto>{

    private final UserRepository userRepository;
    

    protected InputDataMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper, InputData.class, InputDataDto.class);
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        super.modelMapper.createTypeMap(InputData.class, InputDataDto.class)
                .addMappings(m -> m.skip(InputDataDto::setUserId)).setPostConverter(toDtoConverter());


        super.modelMapper.createTypeMap(InputDataDto.class, InputData.class)
                .addMappings(m -> m.skip(InputData::setUserId)).setPostConverter(toEntityConverter());

    }

    @Override
    protected void mapSpecificFields(InputDataDto source, InputData destination) {
        destination.setUserId(userRepository.findById(source.getUserId()).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    protected void mapSpecificFields(InputData source, InputDataDto destination) {
        destination.setUserId(source.getUserId().getId());
    }
}
