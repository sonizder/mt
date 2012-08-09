package com.medyaatak.mt.system.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Ginnun
 * Date: 8/18/11
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "userDao")
@Scope(value = "singleton")
public class UserDaoHibernateImpl implements UserDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<User> getList() {

        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Transactional
    public void delete(User user) {

        entityManager.refresh(user);
        entityManager.remove(user);
    }

    @Transactional
    public User update(User updatedUser) {
        return entityManager.merge(updatedUser);

    }

    @Transactional
    public User save(User newUser) {
        return entityManager.merge(newUser);
    }

    public User findByName(String name) {
        return entityManager.createQuery("SELECT u FROM User u  WHERE u.name = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<User> findByLikeName(String query) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.name LIKE :query or u.firstName LIKE :query or u.lastName LIKE :query", User.class)
                .setParameter("query",'%' +  query + '%')
                .getResultList();
    }

    public User findById(long id) {
        return entityManager.find(User.class, id);
    }
}
