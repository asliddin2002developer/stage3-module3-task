package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.model.impl.NewsModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.*;

import java.util.List;

import com.mjc.school.repository.model.impl.AuthorModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long, Long> {
    private final SessionFactory sessionFactory;
    @Autowired
    public AuthorRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<AuthorModel> readAll() {
        try(Session session = sessionFactory.openSession()){
            return (List<AuthorModel>) session.createQuery("FROM AuthorModel").getResultList();
        }
    }

    @Override
    public AuthorModel readById(Long id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(AuthorModel.class, id);
        }
        catch(NotFoundException e){
            throw e;
        }
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        AuthorModel authorModel = new AuthorModel(entity.getName());
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(authorModel);
            session.getTransaction().commit();
            return authorModel;
        }catch (Exception e){
            e.printStackTrace();
        }
        return authorModel;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        try (Session session  = sessionFactory.openSession()){
            session.beginTransaction();
            AuthorModel authorModel = session.get(AuthorModel.class, entity.getId());
            authorModel.setName(entity.getName());
            session.persist(authorModel);
            session.getTransaction().commit();
            return authorModel;
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<NewsModel> query = session.createQuery("DELETE FROM NewsModel n WHERE n.author.id = :authorId");
            query.setParameter("authorId", id);
            query.executeUpdate();
            AuthorModel authorModel = session.get(AuthorModel.class, id);
            session.remove(authorModel);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean existById(Long id) {
        try (Session session = sessionFactory.openSession()){
            session.get(AuthorModel.class, id);
            return true;
        }
        catch(NotFoundException e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public AuthorModel findById(Long id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(AuthorModel.class, id);
        }catch (Exception e) {
            throw new NotFoundException("Author with given id NOT FOUND");
        }
    }

    @Override
    public List<AuthorModel> getByParam(Long id) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AuthorModel> query = builder.createQuery(AuthorModel.class);
            Root<NewsModel> root = query.from(NewsModel.class);
            Join<NewsModel, AuthorModel> join = root.join("author");
            query.select(join).where(builder.equal(root.get("id"), id));
            return session.createQuery(query).getResultList();
        }
    }
}



