package com.dp.sleep.mapper;

import com.dp.sleep.dto.ConvertedDataDto;
import com.dp.sleep.model.ConvertedData;
import com.dp.sleep.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
public class ConvertedDataMapper extends GenericMapper<ConvertedData, ConvertedDataDto>{

    private final UserRepository userRepository;



    protected ConvertedDataMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper, ConvertedData.class, ConvertedDataDto.class);
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        super.modelMapper.createTypeMap(ConvertedData.class, ConvertedDataDto.class)
                .addMappings(m -> m.skip(ConvertedDataDto::setConvertedUserId)).setPostConverter(toDtoConverter());


        super.modelMapper.createTypeMap(ConvertedDataDto.class, ConvertedData.class)
                .addMappings(m -> m.skip(ConvertedData::setConvertedUserId)).setPostConverter(toEntityConverter());

    }

    @Override
    protected void mapSpecificFields(ConvertedDataDto source, ConvertedData destination) {
        destination.setConvertedUserId(userRepository.findById(source.getConvertedUserId()).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    protected void mapSpecificFields(ConvertedData source, ConvertedDataDto destination) {
        destination.setConvertedUserId(source.getConvertedUserId().getId());
    }
}
