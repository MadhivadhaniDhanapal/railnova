package Railnova.repository;

import Railnova.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByTrainIdAndStatus(Long trainId, String status);
    Seat findByTrainIdAndSeatNumber(Long trainId, Integer seatNumber);
    
}
