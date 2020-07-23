package io.tao.spring;

import io.tao.spring.dao.HibernateDaoImpl;
import io.tao.spring.dao.JdbcDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemo {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        HibernateDaoImpl dao = context.getBean("hibernateDaoImpl", HibernateDaoImpl.class);
        System.out.println(dao.getCircleCount());

//        JdbcDaoImpl dao = context.getBean("jdbcDaoImpl", JdbcDaoImpl.class);
//        dao.createTriangleTable();
//        dao.insertCircle(new Circle(5, "Circle 5"));
//        System.out.println(dao.getAllCircles());
//        System.out.println(dao.getCircleById(1).getName());
//        System.out.println(dao.getCircleCount());
//        System.out.println(dao.getCircleName(1));
    }
}
