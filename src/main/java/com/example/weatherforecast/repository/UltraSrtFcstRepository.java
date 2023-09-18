package com.example.weatherforecast.repository;

import com.example.weatherforecast.entity.UltraSrtFcst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UltraSrtFcstRepository extends JpaRepository<UltraSrtFcst, Integer> {
    UltraSrtFcst save(UltraSrtFcst UltraSrtFcst);
    List<UltraSrtFcst> findAll();
}
