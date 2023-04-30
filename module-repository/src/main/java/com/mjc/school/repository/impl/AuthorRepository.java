package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.model.impl.NewsModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import lombok.*;

import java.util.List;

import com.mjc.school.repository.model.impl.AuthorModel;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<AuthorModel> readAll() {
        return (List<AuthorModel>) entityManager.createQuery("FROM AuthorModel").getResultList();

    }

    @Override
    public AuthorModel readById(Long id) {
            var res = entityManager.find(AuthorModel.class, id);
            return res;
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        AuthorModel authorModel = new AuthorModel(entity.getName());

        entityManager.getTransaction().begin();
        entityManager.persist(authorModel);
        entityManager.getTransaction().commit();
        return authorModel;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
            entityManager.getTransaction().begin();
            AuthorModel authorModel = entityManager.find(AuthorModel.class, entity.getId());
            authorModel.setName(entity.getName());
            entityManager.persist(authorModel);
            entityManager.getTransaction().commit();
            return authorModel;

    }

    @Override
    public boolean deleteById(Long id) {
            entityManager.getTransaction().begin();
            var query = entityManager.createQuery("DELETE FROM NewsModel n " +
                                                                            "WHERE n.author.id = :authorId");
            query.setParameter("authorId", id);
            query.executeUpdate();
            AuthorModel authorModel = entityManager.find(AuthorModel.class, id);
            entityManager.remove(authorModel);
            entityManager.getTransaction().commit();
            return true;
    }

    @Override
    public boolean existById(Long id) {
        try {
            entityManager.find(AuthorModel.class, id);
            return true;
        }
        catch(NotFoundException e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public AuthorModel findById(Long id) {
        try {
            return entityManager.find(AuthorModel.class, id);
        }catch (Exception e) {
            throw new NotFoundException("Author with given id NOT FOUND");
        }
    }

    @Override
    public AuthorModel getAuthorByNewsId(Long id) {
        try{
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<AuthorModel> query = builder.createQuery(AuthorModel.class);
            Root<NewsModel> root = query.from(NewsModel.class);
            Join<NewsModel, AuthorModel> join = root.join("author");
            query.select(join).where(builder.equal(root.get("id"), id));
            return entityManager.createQuery(query).getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new NotFoundException("Author with given id NOT FOUND");
    }
}



