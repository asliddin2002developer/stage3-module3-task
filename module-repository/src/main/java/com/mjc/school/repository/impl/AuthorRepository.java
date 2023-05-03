package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Getter
@Setter
@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public AuthorRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }



    @Override
    public List<AuthorModel> readAll() {
        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<AuthorModel> query = cb.createQuery(AuthorModel.class);
        Root<AuthorModel> fromItem = query.from(AuthorModel.class);
        query.select(fromItem);
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public AuthorModel readById(Long id) {
        return entityManager.find(AuthorModel.class, id);
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
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
        var query = entityManager.createQuery("DELETE FROM NewsModel n WHERE n.author.id = :authorId").setParameter("authorId", id);
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
            entityManager.close();
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



