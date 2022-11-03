package com.garopany.parking.service;

import com.garopany.parking.exception.ParkingNotFoundException;
import com.garopany.parking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap; import java.util.List; import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {
   private static Map<String, Parking> parkingMap = new HashMap<>();

   static {
       String id = getUUID();
       String id1 = getUUID();
       Parking parking = new Parking(id, "DMS-11","SC","CELTA","PRETO");
       Parking parking1 = new Parking(id1, "GRT-11","SC","CELTA","PRETO");
       parkingMap.put(id, parking);
       parkingMap.put(id1, parking1);
   }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }
    public List<Parking> findAll(){ return parkingMap.values().stream().collect(Collectors.toList());
    }
    public Parking findById(String id){
       Parking parking = parkingMap.get(id);
       if(parking == null) throw new ParkingNotFoundException(id);
       return parking;

    }
    public Parking create(Parking parking){
       String uuid = getUUID();
       parking.setId(uuid);
       parking.setEntryDate(LocalDateTime.now());
       parkingMap.put(uuid, parking);
       return parking;
    }

    public void delete(String id) {
       Parking parking = findById(id);
       parkingMap.remove(parking.getId());
    }

    public Parking update(String id, Parking parkingCreate) {
       Parking parking = findById(id);
       if(parkingCreate.getLicense() != null) parking.setLicense(parkingCreate.getLicense());
       if(parkingCreate.getModel() != null) parking.setModel(parkingCreate.getModel());
       if(parkingCreate.getColor() != null) parking.setColor(parkingCreate.getColor());
       if(parkingCreate.getState() != null) parking.setState(parkingCreate.getState());
       parkingMap.replace(id, parking);
       return parking;
    }

    public Parking exit(String id){
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parkingMap.replace(id, parking);
        return parking;
    }
}
