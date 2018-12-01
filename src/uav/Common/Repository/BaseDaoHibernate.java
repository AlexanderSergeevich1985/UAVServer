package uav.Common.Repository;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import uav.Common.DataModels.BaseEntity;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDaoHibernate<T extends BaseEntity, ID extends Serializable> extends HibernateDaoSupport {
    public T getEntity(@Nonnull final ID id) {
        return getHibernateTemplate().get(this.getType(), id);
    }

    public void saveEntity(@Nonnull final T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public void removeEntity(@Nonnull final ID id) {
        T entity = getHibernateTemplate().load(getType(), id);
        if(entity != null) getHibernateTemplate().delete(entity);
    }

    @Nonnull
    public List<?> findAllEntity(@Nonnull final DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public List<T> findAllByExampleEntity(@Nonnull final T example) {
        return getHibernateTemplate().findByExample(example);
    }

    public Class<T> getType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
