package com.infosys.Wheelwise.Controller;


import com.infosys.Wheelwise.Model.Cars;
import com.infosys.Wheelwise.Services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {


    @Autowired
    ProductServices service;



    @GetMapping("/cars")
    public List<Cars> getCars(){
        return service.getCars();
    }


    @GetMapping("/cars/{prodId}")
    public Cars getCarById(@PathVariable int prodId){
        return service.getCarById(prodId);
    }

    @PostMapping("/cars")
    public void addCar(@RequestBody Cars car){
        service.addCar(car);
    }

    @PutMapping("/cars")
    public void updateCar(@RequestBody Cars car){
        service.updateCar(car);
    }

    @DeleteMapping("/cars/{prodId}")
    public void deleteCar(@PathVariable int prodId){
         service.deleteCar(prodId);
    }


}
