package com.dp.sleep.service;

import com.dp.sleep.dto.InputDataAverageDto;
import com.dp.sleep.dto.TimeDto;
import com.dp.sleep.model.InputData;
import com.dp.sleep.repository.InputDataRepository;
import com.dp.sleep.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InputDataService extends GenericService<InputData>{

    private final InputDataRepository repository;
    private final UserRepository userRepository;

    protected InputDataService(InputDataRepository repository, UserRepository userRepository) {
        super(repository);
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public InputDataAverageDto averageInputDataValues(TimeDto timeDto){
        InputDataAverageDto inputDataAverageDto = new InputDataAverageDto();
        List<InputData> list1 = repository.findInputDataByDateBetweenAndUserId(timeDto.getStart(), timeDto.getStop(),
                (userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName())));
//        List<InputData> list1 = repository.findInputDataByDateBetween(timeDto.getStart(), timeDto.getStop());
        inputDataAverageDto.setUserId((userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName())).getId());
        int numOfElements = list1.size();
        double sumTemperature = 0;
        double sumHum = 0;
        for (InputData inputData : list1) {
            sumTemperature = sumTemperature + inputData.getTemperature();
            sumHum = sumHum + inputData.getHumidity();
        }
        inputDataAverageDto.setAverageTemperature(sumTemperature / numOfElements);
        inputDataAverageDto.setAverageHumidity(sumHum / numOfElements);
        return inputDataAverageDto;
    }
}
