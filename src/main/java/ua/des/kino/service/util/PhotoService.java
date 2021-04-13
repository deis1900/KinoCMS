package ua.des.kino.service.util;

import org.springframework.stereotype.Service;
import ua.des.kino.model.submodel.Photo;

import java.util.List;

@Service
public interface PhotoService {

    void save(Photo photo);

    Photo getPhotoByNameAndType(String name, String type);

    List<Photo> getPhotoByName(String name);

    List<Photo> getPhotoByType(String type);

    Photo getPhotoById(Long id);
}
