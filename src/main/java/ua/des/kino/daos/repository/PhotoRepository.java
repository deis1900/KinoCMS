package ua.des.kino.daos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.submodel.Photo;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> getPhotoByNameAndTypePhotos(String name, String type);

    List<Photo> getPhotosByName(String name);

    List<Photo> getPhotosByTypePhotos(String type);
}
