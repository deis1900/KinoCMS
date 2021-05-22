package ua.des.kino.repository.kino;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.des.kino.model.kino.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
