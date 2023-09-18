package com.example.weatherforecast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.example.weatherforecast.entity.MidFcst;

@Repository
public interface MidFcstRepository extends JpaRepository<MidFcst, Integer> {
    MidFcst save(MidFcst midFcst);
    List<MidFcst> findAll();
}
