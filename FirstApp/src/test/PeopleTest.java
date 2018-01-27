package test;

import entity.PeopleEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by Administrator on 2018/1/27 0027.
 */
public class PeopleTest {
    private SessionFactory factory;
    private Session session;
    private Transaction transaction;
    @Before
    public void init(){
        factory = new Configuration().configure().buildSessionFactory();
//        ServiceRegistry service = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
//        factory = con.buildSessionFactory(service);
        session = factory.openSession();
        transaction = session.beginTransaction();
    }
    @After
    public void destroy(){
        transaction.commit();
        session.close();
        factory.close();
    }
    @Test
    public void testSavePeoples(){
        PeopleEntity people   = new PeopleEntity();
        people.setId(0);
        people.setAge(23);
        people.setName("liwei");
        people.setGender("ÄÐ");
        people.setPlace("ËÄ´¨");
        session.save(people);
    }
}
