package uz.pdp.vazifa1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa1.entity.Hotel;
import uz.pdp.vazifa1.repository.HotelRepository;

import java.util.Optional;


@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public Page<Hotel> getHotels( Pageable pageable){
        return hotelRepository.findAll(pageable);
    }

    @PostMapping
    public String post(@RequestBody Hotel hotel){
        if (hotelRepository.existsByName(hotel.getName()))
            return "already exist hotel";
        hotelRepository.save(hotel);
        return "hotel posted";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isPresent()){
            hotelRepository.deleteById(id);
            return "hotel deleted";
        }
        return " hotel not found";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            Hotel hotel1 = new Hotel();
            hotel1.setId(id);
            hotel1.setName(hotel.getName());
            hotelRepository.save(hotel1);

            return "hotel edited";
        }
        return "hotel is not exist";
    }

}
