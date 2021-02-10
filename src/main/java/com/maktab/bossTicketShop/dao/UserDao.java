package com.maktab.bossTicketShop.dao;



import com.maktab.bossTicketShop.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository
public class UserDao extends AbstractEntityDao<User, Integer> {

    @Override
    public Class<User> getClassType() {
        return User.class;
    }

    public  Boolean isValidUser(EntityManager entityManager , String username, String password) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> fromUser = query.from(User.class);
        query.select(fromUser).where(criteriaBuilder.equal(
                fromUser.get("username"),
                username
        ));
        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        User user = null ;
        try {
            user = typedQuery.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }

        entityManager.getTransaction().commit();

        if (user != null){
            System.out.println(user.getPassword());
            System.out.println(password);
            if (user.getPassword().equals(password))
                return true;
            else
                return false ;
        }
        else return false;
    }

    public  User getUserByUsername(String username,EntityManager entityManager){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> fromUser = criteriaQuery.from(User.class);
        criteriaQuery.select(fromUser).where(criteriaBuilder.equal(
                fromUser.get("username"),
                username
        ));
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
