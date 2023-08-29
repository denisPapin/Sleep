package com.dp.sleep.repository;

import com.dp.sleep.model.ConvertedData;
import com.dp.sleep.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface ConvertedDataRepository extends GenericRepository<ConvertedData> {

    ConvertedData findConvertedDataByDate(LocalDate date);

    @Query(
            value = "SELECT * FROM converted_data u WHERE u.user_id = ?1 ORDER BY id desc limit 1",
            nativeQuery = true)
    ConvertedData findConvertedData(Long convertedUserId);



Page<ConvertedData> findAllByDate(LocalDate date, Pageable pageable);

}
