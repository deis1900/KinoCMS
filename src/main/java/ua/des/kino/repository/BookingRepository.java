package ua.des.kino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


}
