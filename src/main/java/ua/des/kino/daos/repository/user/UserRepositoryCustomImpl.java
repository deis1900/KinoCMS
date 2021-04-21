package ua.des.kino.daos.repository.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.des.kino.model.User;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    @Override
    public List<User> getAllUsersData() {
        return null;
    }

    @Override
    public User getUserData(Long id) {
        return null;
    }
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    @Transactional
    public void update(User user){
        em.persist(user);
    }

    @Transactional
        public void remove(Long id) {
        User user = findById(id);
        em.remove(user);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User findUserByJPQL(Long id) {
        return em.createQuery("SELECT u FROM User AS u JOIN FETCH u.contact WHERE u.id=:id", User.class)
                .setParameter("id", id).getSingleResult();
    }

    public User findByEntityGraph(Long id) {
        EntityGraph<User> entityGraph = em.createEntityGraph(User.class);
        entityGraph.addAttributeNodes("name", "contacts");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        return em.find(User.class, id, properties);
    }
}
