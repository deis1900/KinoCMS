package ua.des.kino.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.audience.submodel.Photo;
import ua.des.kino.repository.audience.PhotoRepository;
import ua.des.kino.service.PhotoService;
import ua.des.kino.util.exception_handler.NoSuchElementFoundException;

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

    @Override
    @Transactional
    public boolean isExists(Photo photo) {
        return photoRepository.existsByUrl(photo.getUrl());
    }

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

    @Override
    @Transactional
    public Photo update(Photo photo) {
        return photoRepository.saveAndFlush(photo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        photoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByName(String name) {
        photoRepository.deleteAllByName(name);
    }
}
