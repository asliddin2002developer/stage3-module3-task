package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Setter
@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<TagModel> readAll() {
            return entityManager.createQuery("FROM TagModel").getResultList();
    }

    @Override
    public TagModel readById(Long id) {
            return entityManager.find(TagModel.class, id);
    }

    @Override
    public TagModel create(TagModel entity) {
        TagModel tag = new TagModel(entity.getName());
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public TagModel update(TagModel entity) {
            entityManager.getTransaction().begin();
            TagModel tag = entityManager.find(TagModel.class, entity.getId());
            tag.setName(entity.getName());
            entityManager.getTransaction().commit();
            return tag;
    }

    @Override
    public boolean deleteById(Long id) {
            entityManager.getTransaction().begin();
            TagModel tag = entityManager.find(TagModel.class, id);
            entityManager.remove(tag);
            entityManager.getTransaction().commit();
            return true;
    }

    @Override
    public boolean existById(Long id) {
            entityManager.find(TagModel.class, id);
            return true;
    }

    @Override
    public TagModel findById(Long id) {
            return entityManager.find(TagModel.class, id);
    }

    @Override
    public List<TagModel> getTagsByNewsId(Long id) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TagModel> query = builder.createQuery(TagModel.class);
            Root<NewsModel> root = query.from(NewsModel.class);
            Join<NewsModel, TagModel> join = root.join("tags");
            query.select(join).where(builder.equal(root.get("id"), id));
            return entityManager.createQuery(query).getResultList();
    }
}
