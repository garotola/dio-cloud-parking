package com.garopany.parking.service;

import com.garopany.parking.exception.ParkingNotFoundException;
import com.garopany.parking.model.Parking;
import com.garopany.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap; import java.util.List; import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository repository;
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll(){
        return repository.findAll();
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Parking findById(String id){
        return repository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));
    }
    @Transactional
    public Parking create(Parking parking){
       String uuid = getUUID();
       parking.setId(uuid);
       parking.setEntryDate(LocalDateTime.now());
       repository.save(parking);
       return parking;
    }
    @Transactional
    public void delete(String id) {
       Parking parking = findById(id);
       repository.deleteById(id);
    }
    @Transactional
    public Parking update(String id, Parking parkingCreate) {
       Parking parking = findById(id);
       if(parkingCreate.getLicense() != null) parking.setLicense(parkingCreate.getLicense());
       if(parkingCreate.getModel() != null) parking.setModel(parkingCreate.getModel());
       if(parkingCreate.getColor() != null) parking.setColor(parkingCreate.getColor());
       if(parkingCreate.getState() != null) parking.setState(parkingCreate.getState());
       repository.deleteById(id);
       repository.save(parking);
       return parking;
    }
    @Transactional
    public Parking exit(String id){
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        repository.save(parking);
        return parking;
    }
}
