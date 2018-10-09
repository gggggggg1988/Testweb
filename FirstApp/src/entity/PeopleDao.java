package entity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018/10/9 0009.
 */

@Repository
public class PeopleDao {
    @Autowired//注解
    private HibernateTemplate ht;

    public void save(PeopleEntity people) {
        ht.save(people);

    }
}
