package com.dp.sleep.repository;

import com.dp.sleep.model.Direction;
import com.dp.sleep.model.Meditation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeditationRepository extends GenericRepository<Meditation>{

//    @Query(nativeQuery = true,
//            value = """
//          select distinct b.*
//          from meditations b
//          where b.name ilike '%' || coalesce(:name, '%') || '%'
//          and cast(b.direction as char(2)) like coalesce(:direction,'%')
//          and b.is_deleted = false
//               """)
//    Page<Meditation> searchMeditations(
//            @Param(value = "direction") String direction,
//            @Param(value = "title") String name,
//            Pageable pageable);

    @Query("select m from Meditation m where m.name = ?1 and m.direction = ?2 and m.isDeleted = false")
    Page<Meditation> searchMeditationsByNameAndDirectionAndDeletedIsFalse(String name, Direction direction,
                                                                          Pageable pageable);
}
