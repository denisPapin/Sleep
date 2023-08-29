package com.dp.sleep.service;

import com.dp.sleep.dto.MeditationDto;
import com.dp.sleep.dto.MeditationSearchDto;
import com.dp.sleep.mapper.MeditationMapper;
import com.dp.sleep.model.Direction;
import com.dp.sleep.model.Meditation;
import com.dp.sleep.repository.MeditationRepository;
import com.dp.sleep.utils.FileHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeditationService extends GenericService<Meditation>{

    private final MeditationRepository repository;
    private final MeditationMapper meditationMapper;


    protected MeditationService(MeditationRepository repository, MeditationRepository repository1, MeditationMapper meditationMapper) {
        super(repository);
        this.repository = repository;
        this.meditationMapper = meditationMapper;
    }

    public Meditation create(Meditation meditation, MultipartFile file) throws IOException {
        String fileName = FileHelper.createFile(file);
        meditation.setFileName(fileName);
        return super.create(meditation);
    }

    public Meditation update(Meditation meditation, MultipartFile file) throws IOException {
        String fileName = FileHelper.createFile(file);
        meditation.setFileName(fileName);
        return super.update(meditation);
    }

    @Override
    public Meditation update(Meditation object) {
        Meditation meditation = getOne(object.getId());
        meditation.setName(object.getName() != null ? object.getName() : meditation.getName());
        meditation.setFileName(object.getFileName() != null ? object.getFileName() : meditation.getFileName());
        meditation.setDirection(object.getDirection() != null ? object.getDirection() : meditation.getDirection());
        meditation.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        meditation.setUpdatedWhen(LocalDateTime.now());
        return super.update(meditation);
    }

    public Page<MeditationDto> findMeditations(MeditationSearchDto meditationSearchDto,
                                               Pageable pageable) {
        Direction direction = meditationSearchDto.getDirection();
        Page<Meditation> meditations = repository.searchMeditationsByNameAndDirectionAndDeletedIsFalse(
                meditationSearchDto.getName(),
                direction,
                pageable
        );
        List<MeditationDto> result = meditationMapper.toDtos(meditations.getContent());
        return new PageImpl<>(result, pageable, meditations.getTotalElements());
    }
}
