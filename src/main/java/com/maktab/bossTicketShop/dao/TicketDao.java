package com.maktab.bossTicketShop.dao;

import com.maktab.bossTicketShop.entity.Ticket;
import com.maktab.bossTicketShop.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TicketDao extends AbstractEntityDao<Ticket, Integer> {


    @Override
    public Class<Ticket> getClassType() {
        return Ticket.class;
    }

    public  List<Ticket> getAllTicketOfUser(User user, EntityManager entityManager){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> fromTicket = criteriaQuery.from(Ticket.class);
        criteriaQuery.select(fromTicket).where(
                criteriaBuilder.equal(fromTicket.get("userId"),
                                        user.getId())
        ).where(criteriaBuilder.equal(
                fromTicket.get("isDeleted"),false
        ));
        TypedQuery<Ticket> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList() ;
    }

    public  Ticket deleteTicket(Integer id,EntityManager entityManager){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> fromTicket = criteriaQuery.from(Ticket.class);
        criteriaQuery.select(fromTicket).where(
                criteriaBuilder.equal(fromTicket.get("id"),id)
        );
        TypedQuery<Ticket> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
