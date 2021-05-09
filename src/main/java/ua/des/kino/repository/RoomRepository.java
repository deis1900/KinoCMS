package ua.des.kino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.Room;

import java.util.Collection;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.name = ?1 AND r.cinema.id = ?2")
    Room findByNameAndCinema_Id(String name, Long id);

    Collection<Room> findByCinema_Id(Long id);

    Collection<Room> findAllByCinema_Name(String name);

}
