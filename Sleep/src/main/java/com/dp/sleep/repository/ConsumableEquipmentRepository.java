package com.dp.sleep.repository;

import com.dp.sleep.model.ConsumableEquipment;
import com.dp.sleep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumableEquipmentRepository extends GenericRepository<ConsumableEquipment> {

    ConsumableEquipment findConsumableEquipmentById(Long id);

    @Query(
            value = "SELECT * FROM consumable_equipment u WHERE u.user_id = ?",
            nativeQuery = true)
    ConsumableEquipment findConsumableEquipment(Long equipUserId);
}
