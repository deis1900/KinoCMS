package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.Session;
import ua.des.kino.model.Ticket;

import java.util.List;

@Service
public interface TicketService {

    List<Ticket> getAll();

    Ticket getById(Long id);

    boolean isExists(Long id);

    Ticket saveTicket(Ticket ticket);

    List<Ticket> getSessionTickets(Session session);

    void delete(Long id);
}
