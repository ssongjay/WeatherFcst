package com.example.weatherforecast.repository;

import com.example.weatherforecast.entity.VilageFcst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VilageFcstRepository extends JpaRepository<VilageFcst, Integer> {
    VilageFcst save(VilageFcst vilageFcst);
    List<VilageFcst> findAll();
}
