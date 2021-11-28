package ua.des.kino.service;

import org.springframework.stereotype.Service;
import ua.des.kino.model.mysql.Photo;

import java.util.List;

@Service
public interface PhotoService {

    void save(Photo photo);

    boolean isExists(Photo photo);

    Photo getPhotoByNameAndType(String name, String type);

    List<Photo> getPhotoByName(String name);

    List<Photo> getPhotoByType(String type);

    Photo getPhotoById(Long id);

    Photo update(Photo photo);

    void delete(Long id);

    void deleteAllByName(String name);
}
