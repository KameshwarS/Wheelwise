package com.infosys.Wheelwise.repo;

import com.infosys.Wheelwise.Model.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Cars,Integer> {

}
