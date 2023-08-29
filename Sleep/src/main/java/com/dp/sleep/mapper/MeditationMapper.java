package com.dp.sleep.mapper;

import com.dp.sleep.dto.MeditationDto;
import com.dp.sleep.model.Meditation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MeditationMapper extends GenericMapper<Meditation, MeditationDto>{


    protected MeditationMapper(ModelMapper modelMapper) {
        super(modelMapper, Meditation.class, MeditationDto.class);
    }

    @Override
    protected void mapSpecificFields(MeditationDto source, Meditation destination) {

    }

    @Override
    protected void mapSpecificFields(Meditation source, MeditationDto destination) {

    }
}
