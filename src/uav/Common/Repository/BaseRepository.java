/**The MIT License (MIT)
 Copyright (c) 2018 by AleksanderSergeevich
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package uav.Common.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import uav.Common.DataModels.BaseEntity;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseRepository<T extends BaseEntity, ID extends Serializable> {
    private final SessionFactory sessionFactory;
    private EntityManagerFactory emf;

    public BaseRepository(@Nonnull SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Nonnull
    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Nonnull
    private Session openNewSession() {
        return this.sessionFactory.openSession();
    }

    @Nonnull
    private EntityManager getEM() {
        return this.sessionFactory.getCurrentSession().getEntityManagerFactory().createEntityManager();
    }

    @Nonnull
    private CriteriaBuilder getCriteriaBuilder() {
        return getSession().getCriteriaBuilder();
    }

    public <T> Class<T> getType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    @Nonnull
    public Serializable saveEntity(@Nonnull T entity) {
        return getSession().save(entity);
    }

    public void update(@Nonnull T entity) { //make persistent
        getSession().update(entity);
    }

    @Nonnull
    public T merge(@Nonnull T entity) { //merge and make persistent
        return (T) getSession().merge(entity);
    }

    public void detachOne(@Nonnull T entity) {
        getSession().evict(entity);
    }

    public void detachAll() {
        getSession().clear();
    }

    public void deleteEntity(@Nonnull T entity) {
        getSession().delete(entity);
    }

    public void flush() {
        getSession().flush();
    }

    public void close() {
        this.sessionFactory.close();
    }

    @Nonnull
    public Query createQuery(@Nonnull String queryString) {
        return getSession().createQuery(queryString);
    }

    @Nonnull
    public Query<T> createQuery(@Nonnull CriteriaQuery<T> criteriaQuery) {
        return getSession().createQuery(criteriaQuery);
    }

    @Nonnull
    public CriteriaQuery<T> createCriteriaQuery(@Nonnull final Specification<T> specification) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = getCriteriaBuilder().createQuery(this.getType());
        Root<T> root = criteriaQuery.from(this.getType());
        criteriaQuery.select(root);
        Predicate predicate = specification.toPredicate(root, criteriaQuery, criteriaBuilder);
        return criteriaQuery.where(predicate);
    }

    public List<T> findAllBySpecification(@Nonnull final Specification<T> specification) {
        return createQuery(createCriteriaQuery(specification)).getResultList();
    }
}
