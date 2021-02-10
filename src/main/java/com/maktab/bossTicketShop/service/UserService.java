package com.maktab.bossTicketShop.service;

import com.maktab.bossTicketShop.dao.UserDao;
import com.maktab.bossTicketShop.entity.User;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


@Service
public class UserService {

    private UserDao userDao ;

    private EntityManagerFactory entityManagerFactory ;


    @Autowired
    public UserService(UserDao userDao,EntityManagerFactory entityManagerFactory) {
        this.userDao = userDao;
        this.entityManagerFactory = entityManagerFactory ;
    }

    public boolean isValidUser(String userName , String password){
        boolean isValid = false;
        EntityManager entityManager = entityManagerFactory.createEntityManager() ;
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (userDao.isValidUser(entityManager,userName,password)){
            isValid = true ;
        }
        entityManager.close();
        return isValid ;
    }

    public User getUserByUsername(String userName){
        User user = null ;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        user = userDao.getUserByUsername(userName,entityManager);
        entityManager.close();
        return user ;
    }




}
