package uz.pdp.vazifa1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.vazifa1.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = "select * from room where hotel_id = :hotelId", nativeQuery = true)
    Page<Room> findAllByHotelIdNative(Integer hotelId, Pageable pageable);
}
