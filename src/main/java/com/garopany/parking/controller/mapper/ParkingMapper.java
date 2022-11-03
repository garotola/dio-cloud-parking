package com.garopany.parking.controller.mapper;

import com.garopany.parking.controller.dto.ParkingCreateDTO;
import com.garopany.parking.controller.dto.ParkingDTO;
import com.garopany.parking.model.Parking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingMapper {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public ParkingDTO parkingDTO(Parking parking){
        return MODEL_MAPPER.map(parking, ParkingDTO.class);
    }
    public List<ParkingDTO> toParkingDTOList(List<Parking> parkingList){
        return parkingList.stream().map(this::parkingDTO).collect(Collectors.toList());
    }

    public Parking toParking(ParkingDTO dto) {
        return MODEL_MAPPER.map(dto, Parking.class);
    }

    public Parking toParkingCreate(ParkingCreateDTO dto) {
        return MODEL_MAPPER.map(dto, Parking.class);
    }
}
