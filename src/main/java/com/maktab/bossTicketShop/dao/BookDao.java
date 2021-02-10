package com.maktab.bossTicketShop.dao;



import com.maktab.bossTicketShop.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BookDao extends AbstractEntityDao<Book, Integer> {

    @Override
    public Class<Book> getClassType() {
        return Book.class;
    }

    public List<Book> getBooksByDateAndCity(String date, String destinationCity, String originCity, EntityManager entityManager){

        List<Book> books ;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> fromBook = query.from(Book.class);
        query.select(fromBook).where(criteriaBuilder.equal(
                fromBook.get("originCity"),originCity
                            )).where(criteriaBuilder.equal(
                fromBook.get("destinationCity"),destinationCity
                            )).where(criteriaBuilder.equal(
                fromBook.get("date"),date
        ));
        TypedQuery<Book> typedQuery = entityManager.createQuery(query);
        books = typedQuery.getResultList() ;
        return books ;

    }

}
