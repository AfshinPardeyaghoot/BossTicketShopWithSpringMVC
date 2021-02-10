package com.maktab.bossTicketShop.dao;

import javax.persistence.EntityManager;


public abstract class AbstractEntityDao<T, U> {



    public T load(EntityManager entityManager , U id) {
        return entityManager.find(getClassType(), id);
    }

    public void save(EntityManager entityManager , T t) {
        entityManager.persist(t);
    }

    public void delete(EntityManager entityManager , T t) {
        entityManager.remove(t);
    }

    public void update(EntityManager entityManager , T t) {
        entityManager.merge(t);
    }

    public abstract Class<T> getClassType();

}
