package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Setter
@Repository
public class TagRepository implements BaseRepository<TagModel, Long, Long> {

    private final SessionFactory sessionFactory;

    @Autowired
    public TagRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TagModel> readAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("FROM TagModel").getResultList();
        }
    }

    @Override
    public TagModel readById(Long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(TagModel.class, id);
        }
    }

    @Override
    public TagModel create(TagModel entity) {
        TagModel tag = new TagModel(entity.getName());
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    public TagModel update(TagModel entity) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            TagModel tag = session.get(TagModel.class, entity.getId());
            tag.setName(entity.getName());
            session.getTransaction().commit();
            return tag;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            TagModel tag = session.get(TagModel.class, id);
            session.remove(tag);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean existById(Long id) {
        try(Session session = sessionFactory.openSession()){
            session.get(TagModel.class, id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public TagModel findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(TagModel.class, id);
        }
        catch (Exception e){
            throw new NotFoundException("Tag with given ID NOT FOUND");
        }
    }

    @Override
    public List<TagModel> getByParam(Long id) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TagModel> query = builder.createQuery(TagModel.class);
            Root<NewsModel> root = query.from(NewsModel.class);
            Join<NewsModel, TagModel> join = root.join("tags");
            query.select(join).where(builder.equal(root.get("id"), id));
            return session.createQuery(query).getResultList();

        }
    }
}
