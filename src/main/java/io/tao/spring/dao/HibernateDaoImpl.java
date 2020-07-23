package io.tao.spring.dao;

import lombok.Data;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class HibernateDaoImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public int getCircleCount() {
        String hql = "SELECT COUNT(*) FROM Circle";
        Query query = getSessionFactory().openSession().createQuery(hql);
        return ((Long) query.uniqueResult()).intValue();
    }
}
