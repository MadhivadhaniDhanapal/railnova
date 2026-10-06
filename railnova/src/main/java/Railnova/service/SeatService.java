package Railnova.service;

import Railnova.entity.Seat;
import Railnova.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat allocateSeat(Long trainId) {

        List<Seat> availableSeats =
                seatRepository.findByTrainIdAndStatus(trainId, "AVAILABLE");

        if (availableSeats.isEmpty()) {
            return null;
        }

        Seat seat = availableSeats.get(0);

        seat.setStatus("BOOKED");

        return seatRepository.save(seat);
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
}