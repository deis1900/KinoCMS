package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.submodel.Room;

import java.util.List;

@Service
public interface RoomService {

    boolean isExists(Long id);

    Room findById(Long id);

    Long save(Room room);

    Room update(Room room);

    void delete(Long id);

    List<Room> findAll();

    Room findDuplicate(String name, Long id);
}
