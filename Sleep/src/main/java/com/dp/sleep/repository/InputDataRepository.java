package com.dp.sleep.repository;

import com.dp.sleep.model.InputData;
import com.dp.sleep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InputDataRepository extends GenericRepository<InputData> {

//    @Query("select i from InputData i where i.date between ?1 and ?2 and i.userId = ?3")
    List<InputData> findInputDataByDateBetweenAndUserId(LocalDateTime date, LocalDateTime date2, User userId);

    List<InputData> findInputDataByDateBetween(LocalDateTime one, LocalDateTime two);

}
