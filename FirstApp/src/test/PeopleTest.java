package test;

import entity.PeopleEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.Blob;


/**
 * Created by Administrator on 2018/1/27 0027.
 */
public class PeopleTest {
    private SessionFactory factory;
    private Session session;
    private Transaction transaction;
    private Blob blob;

    @Before
    public void init(){
        factory = new Configuration().configure().buildSessionFactory();//ʹ������ķ�����factory�Ļ�����������
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
    public void testSavePeoples() throws Exception{
        PeopleEntity people   = new PeopleEntity();
        people.setId(0);
        people.setAge(23);
        people.setName("liwei");
        people.setGender("��");
        people.setPlace("�Ĵ�");
        session.save(people);
//        File f = new File("path");//保存图片等二进制文件方法
//        InputStream is = new FileInputStream(f);
//        blob = Hibernate.getLobCreator(session).createBlob(is, is.available());
//        people.setBlob(blob);

        //读取数据库文件 数据
//        PeopleEntity p =  session.get(PeopleEntity.class, 1);
//        Blob img =  p.getBlob();
//        InputStream binaryStream = img.getBinaryStream();
//        File outFile = new File("path");
//        OutputStream os  = new FileOutputStream(outFile);
//        byte[] buff = new byte[binaryStream.available()];
//        binaryStream.read(buff);
//        os.write(buff);
//        binaryStream.close();
//        os.close();

    }
}
