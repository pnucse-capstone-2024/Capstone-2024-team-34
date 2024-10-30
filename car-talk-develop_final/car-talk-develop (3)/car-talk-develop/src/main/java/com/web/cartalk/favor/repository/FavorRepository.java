package com.web.cartalk.favor.repository;

import com.web.cartalk.favor.Favor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavorRepository extends JpaRepository<Favor, Long> {

    @Modifying
    @Query("DELETE FROM Favor f WHERE f.user.id = :userId")
    void deleteAllByUserId(Long userId);

    @Query("SELECT f FROM Favor f JOIN FETCH f.vehicle fo WHERE f.user.id=:userId")
    List<Favor> findByUserId(Long userId);

    //void saveUserVehiclePreferences(Long id, List<Long> storedVehicleIds);

    //List<Long> findVehicleIdsByUserId(Long id);
}
