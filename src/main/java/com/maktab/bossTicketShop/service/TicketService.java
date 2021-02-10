package com.maktab.bossTicketShop.service;

import com.maktab.bossTicketShop.dao.TicketDao;
import com.maktab.bossTicketShop.entity.Book;
import com.maktab.bossTicketShop.entity.Ticket;
import com.maktab.bossTicketShop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class TicketService {

    private TicketDao ticketDao ;

    private EntityManagerFactory entityManagerFactory ;

    @Autowired
    public TicketService(TicketDao ticketDao, EntityManagerFactory entityManagerFactory) {
        this.ticketDao = ticketDao;
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createNewTicket(Book book, String gender, String ownerName, User user){
        Ticket ticket = new Ticket();
        ticket.setBookId(book);
        ticket.setGender(gender);
        ticket.setOwnerName(ownerName);
        ticket.setUserId(user);
        ticket.setDeleted(false);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ticketDao.save(entityManager,ticket);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Ticket> getUserAllTickets(User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Ticket> tickets = ticketDao.getAllTicketOfUser(user,entityManager);
        entityManager.getTransaction().commit();
        entityManager.close();
        return tickets;
    }

    public Ticket getTicketById(Integer ticketId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TicketDao ticketDao = new TicketDao();
        return ticketDao.load(entityManager,ticketId);
    }

    public void deleteTicketById(Integer tickectId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TicketDao ticketDao = new TicketDao();
        Ticket ticket = ticketDao.deleteTicket(tickectId,entityManager);
        ticket.setDeleted(true);
        ticketDao.update(entityManager , ticket);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
