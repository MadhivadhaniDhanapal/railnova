package Railnova.service;

import Railnova.entity.Booking;
import Railnova.repository.BookingRepository;
import Railnova.repository.SeatRepository;
import Railnova.entity.Passenger;
import Railnova.repository.PassengerRepository;
import Railnova.entity.Seat;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;

    public BookingService(BookingRepository bookingRepository,
                           PassengerRepository passengerRepository,
                           SeatRepository seatRepository) {

        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.seatRepository = seatRepository;
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking == null) {
            return null;
        }

        booking.setStatus("CANCELLED");

        Passenger passenger =
                passengerRepository.findAll()
                .stream()
                .filter(p -> p.getBookingId().equals(bookingId))
                .findFirst()
                .orElse(null);

        if (passenger != null) {

            Seat seat = seatRepository.findByTrainIdAndSeatNumber(
                    booking.getTrainId(),
                    passenger.getSeatNumber()
            );

            if (seat != null) {
                seat.setStatus("AVAILABLE");
                seatRepository.save(seat);
            }
        }

        return bookingRepository.save(booking);
    }
}