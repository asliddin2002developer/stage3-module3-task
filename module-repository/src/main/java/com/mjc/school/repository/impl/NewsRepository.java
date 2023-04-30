package com.mjc.school.repository.impl;


import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;

import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.repository.utils.NewsParams;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import lombok.*;

import org.springframework.stereotype.Repository;

import java.util.List;


@Getter
@Setter
@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<NewsModel> readAll() {

            // query for FetchType.LAZY
            // return entityManager.createQuery("SELECT DISTINCT  n FROM NewsModel n " +
            //                                                   "JOIN FETCH n.tags t " +
            //                                                   "JOIN FETCH n.author a ", NewsModel.class).getResultList();
            return entityManager.createQuery("FROM NewsModel").getResultList();

    }

    @Override
    public NewsModel readById(Long id) {
        return entityManager.find(NewsModel.class, id);

    }

    @Override
    public NewsModel create(NewsModel entity) {
            entityManager.getTransaction().begin();

            AuthorModel authorModel = entityManager.merge(entity.getAuthor());
            entity.setAuthor(authorModel);
            entityManager.persist(entity);

            entityManager.getTransaction().commit();
            return entity;
    }
    @Override
    public NewsModel update(NewsModel entity) {
            entityManager.getTransaction().begin();
            System.out.println(entity.getTags());
            NewsModel response = entityManager.find(NewsModel.class, entity.getId());
            response.setTitle(entity.getTitle());
            response.setContent(entity.getContent());
            response.setAuthor(entity.getAuthor());
            response.setTags(entity.getTags());

            entityManager.getTransaction().commit();

            return response;
    }
    @Override
    public boolean deleteById(Long id) {
            entityManager.getTransaction().begin();

            NewsModel news = entityManager.find(NewsModel.class, id);

             //   get all tags that news has alone
             Query q = entityManager.createNativeQuery("SELECT nt.tag_id FROM newstag nt " +
                                                                             "JOIN tag t ON nt.tag_id = t.id " +
                                                                             "JOIN newstag nt2 ON t.id = nt2.tag_id " +
                                                                             "WHERE nt2.news_id = ? " +
                                                                             "GROUP BY nt.tag_id " +
                                                                            "HAVING COUNT(nt.news_id) = 1");
             q.setParameter(1, news.getId());
             List<Long> tagIds = (List<Long>) q.getResultList();

            // remove all associations for this news
            q = entityManager.createNativeQuery("DELETE FROM newstag nt " +
                                                            "WHERE nt.news_id = :id");
            q.setParameter("id", news.getId());
            q.executeUpdate();

            // remove all tags that this news has alone
            q = entityManager.createNativeQuery("DELETE FROM tag t " +
                                                            "WHERE t.id IN (:ids)");
            q.setParameter("ids", tagIds);
            q.executeUpdate();

            entityManager.remove(news);

            entityManager.getTransaction().commit();
            return true;
    }
    @Override
    public boolean existById(Long id) {
            entityManager.find(NewsModel.class, id);
            return true;
    }

    @Override
    public NewsModel findById(Long id){
            return entityManager.find(NewsModel.class, id);
    }

    @Override
    public List<NewsModel> getNewsByParams(NewsParams params) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
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

            return entityManager.createQuery(cq).getResultList();
    }
}
