package ua.des.kino.repository.audience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.des.kino.model.audience.submodel.Photo;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    boolean existsByUrl(String url);

    Optional<Photo> getPhotoByNameAndTypePhotos(String name, String type);

    List<Photo> getPhotosByName(String name);

    List<Photo> getPhotosByTypePhotos(String type);

    void deleteAllByName(String name);
}
