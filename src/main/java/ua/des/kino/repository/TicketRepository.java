package ua.des.kino.repository;

import org.springframework.data.repository.Repository;
import ua.des.kino.model.Ticket;

@org.springframework.stereotype.Repository
public interface TicketRepository extends Repository<Ticket, Long> {
}
