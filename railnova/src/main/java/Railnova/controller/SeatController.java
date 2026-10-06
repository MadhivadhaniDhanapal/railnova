package Railnova.controller;

import Railnova.entity.Seat;
import Railnova.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @PostMapping
    public Seat createSeat(@RequestBody Seat seat) {
        return seatService.saveSeat(seat);
    }
}