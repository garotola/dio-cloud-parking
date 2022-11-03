package com.garopany.parking.controller;

import com.garopany.parking.controller.dto.ParkingCreateDTO;
import com.garopany.parking.controller.dto.ParkingDTO;
import com.garopany.parking.controller.mapper.ParkingMapper;
import com.garopany.parking.model.Parking;
import com.garopany.parking.service.ParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@Api(tags = "Parking Controller")
public class ParkingController {
    private final ParkingService parkingService;
    @Autowired
    private ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService){
        this.parkingService = parkingService;
    }
    @GetMapping
    @ApiOperation("Find all parkings")
    public ResponseEntity<List<ParkingDTO>> findAll() {
        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id){
        Parking parking = parkingService.findById(id);
        ParkingDTO parkingDTO = parkingMapper.parkingDTO(parking);
        return ResponseEntity.ok(parkingDTO);
    }
    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto){
        Parking parkingCreate = parkingMapper.toParkingCreate(dto);
        Parking parking = parkingService.create(parkingCreate);
        ParkingDTO result = parkingMapper.parkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO dto){
        Parking parkingCreate = parkingMapper.toParkingCreate(dto);
        Parking parking = parkingService.update(id, parkingCreate);
        ParkingDTO result = parkingMapper.parkingDTO(parking);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PostMapping("/{id}")
    public ResponseEntity<ParkingDTO> exit(@PathVariable String id){
        Parking parking = parkingService.exit(id);
        return ResponseEntity.ok().body(parkingMapper.parkingDTO(parking));
    }
}
