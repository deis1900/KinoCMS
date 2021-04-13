package ua.des.kino.service.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.submodel.Photo;
import ua.des.kino.repository.PhotoRepository;
import ua.des.kino.util.exception_handler.session.NoSuchElementFoundException;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    @Transactional
    public void save(Photo photo) {
        photoRepository.save(photo);
    }

    //todo THROWABLE rewrite
    @Override
    @Transactional
    public Photo getPhotoByNameAndType(String name, String type) {
        return photoRepository.getPhotoByNameAndTypePhotos(name, type).orElseThrow(() ->
                new NoSuchElementFoundException("Photo with name: " + name + " & type " +type, new Throwable()));
    }

    @Override
    @Transactional
    public List<Photo> getPhotoByName(String name) {
        return photoRepository.getPhotosByName(name);
    }

    @Override
    @Transactional
    public List<Photo> getPhotoByType(String type) {
        return photoRepository.getPhotosByTypePhotos(type);
    }

    @Override
    @Transactional
    public Photo getPhotoById(Long id){
        return photoRepository.getOne(id);
    }
}
