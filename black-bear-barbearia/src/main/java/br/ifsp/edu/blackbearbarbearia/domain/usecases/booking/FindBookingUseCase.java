package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

import java.util.List;

public class FindBookingUseCase {
    private final BookingDAO dao;

    public FindBookingUseCase(BookingDAO dao) {
        this.dao = dao;
    }

    public List<Booking> findAll() {
        if (dao.findAll().isEmpty())
            throw new EntityNotFoundException("Bookings not found");

        return dao.findAll();
    }
}
