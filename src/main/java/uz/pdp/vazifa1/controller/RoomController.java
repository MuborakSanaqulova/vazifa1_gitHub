package uz.pdp.vazifa1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa1.dto.RoomDto;
import uz.pdp.vazifa1.entity.Hotel;
import uz.pdp.vazifa1.entity.Room;
import uz.pdp.vazifa1.repository.HotelRepository;
import uz.pdp.vazifa1.repository.RoomRepository;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public Page<Room> getRooms(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @GetMapping("/byHotelId/{hotelId}")
    public Page<Room> getRoomByHotelId(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable, @PathVariable Integer hotelId) throws Exception {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            return roomRepository.findAllByHotelIdNative(hotelId, pageable);
        }
        throw new Exception("xatolik");
    }


    @PostMapping
    public String post(@RequestBody RoomDto roomDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()){
            Room room = new Room();
            room.setNumber(roomDto.getNumber());
            room.setFloor(roomDto.getFloor());
            room.setSize(roomDto.getSize());
            room.setHotel(optionalHotel.get());
            roomRepository.save(room);

            return "room posted";
        }
        return "hotel is not exist";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            roomRepository.deleteById(id);
            return "room deleted";
        }
        return "room not found";
    }

    @PutMapping
    public String edit(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
            if (optionalHotel.isPresent()){
                Room room = new Room();
                room.setId(id);
                room.setNumber(roomDto.getNumber());
                room.setFloor(roomDto.getFloor());
                room.setSize(roomDto.getSize());
                room.setHotel(optionalHotel.get());

                roomRepository.save(room);
                return "edited";
            }
            return "hotel not found";
        }
        return "room not found";
    }
}
