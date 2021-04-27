package ua.des.kino.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.submodel.Room;
import ua.des.kino.repository.RoomRepository;
import ua.des.kino.service.RoomService;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

import java.util.List;

public class RoomServiceImpl implements RoomService {

    public static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class.getName());

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public boolean isExists(Long id) {
        return roomRepository.existsById(id);
    }

    @Override
    @Transactional
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException("Room with id: " + id + " is not found", new Throwable()));
    }

    @Override
    @Transactional
    public Room findDuplicate(String name, Long id) {
        return roomRepository.findByNameAndCinema_Id(name, id);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    @Transactional
    public Long save(Room room) {
        Room r = roomRepository.save(room);
        return r.getId();
    }

    @Override
    @Transactional
    public Room update(Room room) {
        return roomRepository.saveAndFlush(room);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }
}
