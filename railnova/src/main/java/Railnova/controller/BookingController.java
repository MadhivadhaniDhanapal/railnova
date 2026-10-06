package Railnova.controller;

import Railnova.entity.Booking;
import Railnova.entity.Passenger;
import Railnova.entity.Seat;
import Railnova.entity.Train;
import Railnova.service.BookingService;
import Railnova.service.PassengerService;
import Railnova.service.SeatService;
import Railnova.service.TrainService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;
    private final PassengerService passengerService;
    private final SeatService seatService;
    private final TrainService trainService;

    public BookingController(BookingService bookingService,
                         PassengerService passengerService,
                         SeatService seatService,
                         TrainService trainService) {

    this.bookingService = bookingService;
    this.passengerService = passengerService;
    this.seatService = seatService;
    this.trainService = trainService;
}

    // Get all bookings - Booking History
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Create booking
    @PostMapping
public Booking createBooking(@RequestBody Booking booking) {

    booking.setPnr("RN" + UUID.randomUUID()
            .toString()
            .substring(0, 8)
            .toUpperCase());

    if (booking.getTravelDate() == null) {
        booking.setTravelDate(LocalDate.now());
    }

    booking.setStatus("CONFIRMED");

    Booking savedBooking = bookingService.saveBooking(booking);

    int count = booking.getTotalPassengers();

    int allocated = 0;

    for (int i = 0; i < count; i++) {

        Seat allocatedSeat =
                seatService.allocateSeat(booking.getTrainId());

        if (allocatedSeat == null) {
            break;
        }

        Passenger passenger = new Passenger();

        passenger.setBookingId(savedBooking.getId());
        passenger.setName(booking.getPassengerName());
        passenger.setAge(booking.getPassengerAge());
        passenger.setBerth(allocatedSeat.getBerthType());
        passenger.setSeatNumber(allocatedSeat.getSeatNumber());

        passengerService.savePassenger(passenger);

        allocated++;
    }

    if (allocated < count) {

        savedBooking.setStatus(
                "INSUFFICIENT SEATS"
        );

    } else {

        savedBooking.setStatus("CONFIRMED");
    }

    return bookingService.saveBooking(savedBooking);
}
    @PutMapping("/{id}/cancel")
public Booking cancelBooking(@PathVariable Long id) {

    return bookingService.cancelBooking(id);
}
}