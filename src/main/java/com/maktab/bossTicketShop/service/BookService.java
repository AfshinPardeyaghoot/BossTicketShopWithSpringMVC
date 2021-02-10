package com.maktab.bossTicketShop.service;

import com.maktab.bossTicketShop.dao.BookDao;
import com.maktab.bossTicketShop.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class BookService {

    private BookDao bookDao ;

    private EntityManagerFactory entityManagerFactory ;

    @Autowired
    public BookService(BookDao bookDao, EntityManagerFactory entityManagerFactory) {
        this.bookDao = bookDao;
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Book> getBooksByDestinationAndOrgCityAndDate(String orgCity, String desCity, String date){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Book> books = bookDao.getBooksByDateAndCity(date,desCity,orgCity,entityManager);
        entityManager.getTransaction().commit();
        entityManager.close();
        return books ;
    }

}
