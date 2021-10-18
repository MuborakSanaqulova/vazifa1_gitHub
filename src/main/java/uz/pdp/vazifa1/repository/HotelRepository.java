package uz.pdp.vazifa1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vazifa1.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    boolean existsByName(String name);
}
