package com.dp.sleep.service;

import com.dp.sleep.dto.*;
import com.dp.sleep.mapper.ConvertedDataMapper;
import com.dp.sleep.model.ConvertedData;
import com.dp.sleep.model.Direction;
import com.dp.sleep.model.Meditation;
import com.dp.sleep.repository.ConvertedDataRepository;
import com.dp.sleep.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ConvertedDataService extends GenericService<ConvertedData> {

    private final UserRepository userRepository;

    private final ConvertedDataRepository repository;

    private final ConvertedDataMapper mapper;

    protected ConvertedDataService(UserRepository userRepository, ConvertedDataRepository repository, ConvertedDataMapper mapper) {
        super(repository);
        this.userRepository = userRepository;
        this.repository = repository;
        this.mapper = mapper;
    }

    public ConvertedData convertData(InputDataAverageDto inputDataAverageDto, TimeDto timeDto){
        ConvertedDataDto convertedDataDto = new ConvertedDataDto();
        convertedDataDto.setSleepQuality(String.valueOf(inputDataAverageDto.getAverageHumidity() + inputDataAverageDto.getAverageTemperature()));
        convertedDataDto.setSleepDuration(ChronoUnit.SECONDS.between(timeDto.getStart(), timeDto.getStop()));
        convertedDataDto.setConvertedUserId(inputDataAverageDto.getUserId());
        convertedDataDto.setDate(LocalDate.now());
        return repository.save(mapper.toEntity(convertedDataDto));
    }

    public ConvertedDataDto take(LocalDate date){
        return mapper.toDto(repository.findConvertedDataByDate(date));
    }

    public Page<ConvertedDataDto> findSleep(ConvertedDataSearchDto convertedDataSearchDto,
                                               Pageable pageable) {
        LocalDate localDate = convertedDataSearchDto.getDate();
        Page<ConvertedData> convertedDates = repository.findAllByDate(
               localDate,
                pageable
        );
        List<ConvertedDataDto> result = mapper.toDtos(convertedDates.getContent());
        return new PageImpl<>(result, pageable, convertedDates.getTotalElements());
    }

    public ConvertedData findByUserIdAndDate(Long userId){
        return repository.findConvertedData(userId);
    }


}
