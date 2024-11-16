package com.infosys.Wheelwise.Services;

import com.infosys.Wheelwise.Model.Cars;
import com.infosys.Wheelwise.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ProductServices {


    @Autowired
    CarRepository carRepo;

//    List<Cars> cars= new ArrayList<>(Arrays.asList(new Cars(101,"Toyota",5000)));

    public List<Cars> getCars(){
        return carRepo.findAll();
    }


    public Cars getCarById(int prodId){
        return carRepo.findById(prodId)
                .orElse(new Cars()) ;   }

    public void addCar(Cars car){
        carRepo.save(car);
    }


    public void updateCar(Cars car) {
        carRepo.save(car);
    }

    public void deleteCar(int prodId) {
        carRepo.deleteById(prodId);
    }
}
