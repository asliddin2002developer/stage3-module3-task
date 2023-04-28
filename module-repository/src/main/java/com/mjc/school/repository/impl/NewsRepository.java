package com.mjc.school.repository.impl;


import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;

import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.repository.utils.NewsParams;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Getter
@Setter
@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    private final SessionFactory sessionFactory;
    @Autowired
    public NewsRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<NewsModel> readAll() {
        try(Session session = sessionFactory.openSession()){
            // query for FetchType.LAZY
            // return session.createQuery("SELECT DISTINCT  n FROM NewsModel n " +
            //                                                   "JOIN FETCH n.tags t " +
            //                                                   "JOIN FETCH n.author a ", NewsModel.class).getResultList();
            return session.createQuery("FROM NewsModel").getResultList();
        }
    }

    @Override
    public NewsModel readById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(NewsModel.class, id);
        }
    }

    @Override
    public NewsModel create(NewsModel entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            AuthorModel authorModel = session.merge(entity.getAuthor());
            entity.setAuthor(authorModel);
            session.persist(entity);

            session.getTransaction().commit();
            return entity;
        }
    }
    @Override
    public NewsModel update(NewsModel entity) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            System.out.println(entity.getTags());
            NewsModel response = session.get(NewsModel.class, entity.getId());
            response.setTitle(entity.getTitle());
            response.setContent(entity.getContent());
            response.setAuthor(entity.getAuthor());
            response.setTags(entity.getTags());

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return entity;
    }
    @Override
    public boolean deleteById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            NewsModel news = session.get(NewsModel.class, id);

             //   get all tags that news has alone
             Query q = session.createNativeQuery("SELECT nt.tag_id FROM newstag nt " +
                                                                             "JOIN tag t ON nt.tag_id = t.id " +
                                                                             "JOIN newstag nt2 ON t.id = nt2.tag_id " +
                                                                             "WHERE nt2.news_id = ? " +
                                                                             "GROUP BY nt.tag_id " +
                                                                            "HAVING COUNT(nt.news_id) = 1");
             q.setParameter(1, news.getId());
             List<Long> tagIds = (List<Long>) q.getResultList();

            // remove all associations for this news
            q = session.createNativeQuery("DELETE FROM newstag nt " +
                                                            "WHERE nt.news_id = :id");
            q.setParameter("id", news.getId());
            q.executeUpdate();

            // remove all tags that this news has alone
            q = session.createNativeQuery("DELETE FROM tag t " +
                                                            "WHERE t.id IN (:ids)");
            q.setParameter("ids", tagIds);
            q.executeUpdate();

            session.remove(news);

            session.getTransaction().commit();
            return true;
        }
    }
    @Override
    public boolean existById(Long id) {
        try(Session session = sessionFactory.openSession()){
            session.get(NewsModel.class, id);
            return true;
        }catch (NotFoundException e){
            return false;
        }
    }

    @Override
    public NewsModel findById(Long id){
        try(Session session = sessionFactory.openSession()){
            return session.get(NewsModel.class, id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        throw new NotFoundException("News with given id NOT FOUND");
    }

    @Override
    public List<NewsModel> getNewsByParams(NewsParams params) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<NewsModel> cq = cb.createQuery(NewsModel.class);
            Root<NewsModel> newsRoot = cq.from(NewsModel.class);
            Join<NewsModel, TagModel> tagJoin = newsRoot.join("tags");

            Predicate authorPredicate = null;
            if(params.getAuthorName() != null && !params.getAuthorName().isEmpty()){
                Join<NewsModel, AuthorModel> authorJoin = newsRoot.join("author");
                authorPredicate = cb.like(authorJoin.get("name"), "%" + params.getAuthorName() + "%");
            }

            Predicate titlePredicate = null;
            if(params.getTitle() != null && !params.getTitle().isEmpty()){
                titlePredicate = cb.like(newsRoot.get("title"), "%" + params.getTitle() + "%");
            }

            Predicate contentPredicate = null;
            if(params.getContent() != null && !params.getContent().isEmpty()){
                contentPredicate = cb.like(newsRoot.get("content"), "%" + params.getContent() + "%");
            }

            Predicate tagIdPredicate = null;
            if(params.getTagIds() != null && !params.getTagIds().isEmpty()){
                tagIdPredicate = tagJoin.get("id").in(params.getTagIds());
            }

            Predicate tagNamePredicate = null;
            if(params.getTagNames() != null && !params.getTagNames().isEmpty()){
                tagNamePredicate = tagJoin.get("name").in(params.getTagNames());
            }

            Predicate finalPredicate = cb.and(authorPredicate, titlePredicate, contentPredicate, tagIdPredicate, tagNamePredicate);
            cq.where(finalPredicate);

            return session.createQuery(cq).getResultList();
        }
    }
}