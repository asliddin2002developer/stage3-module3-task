package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Getter
@Setter
@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    @Autowired
    public TagRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }


    @Override
    public List<TagModel> readAll() {
            return entityManager.createNativeQuery("select * from tag", TagModel.class).getResultList();
    }

    @Override
    public TagModel readById(Long id) {
            return entityManager.find(TagModel.class, id);
    }

    @Override
    public TagModel create(TagModel entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public TagModel update(TagModel entity) {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("UPDATE TagModel t SET t.name = :tagName WHERE t.id = :tagId")
                    .setParameter("tagName", entity.getName()).setParameter("tagId", entity.getId());
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return entity;
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
