package com.lanou.domain;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import utils.HibernateUtil;

/**
 * Created by dllo on 17/11/1.
 */
public class CacheTest {
    @Test
    public void test() {
        //验证二级缓存
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s1 = sf.openSession();
        s1.beginTransaction();

        //第一次取数据
        Student s = s1.get(Student.class, 1);
        System.out.println(s);

        s1.getTransaction().commit();
        s1.close();


        //第二次取数据
        Session s2 = sf.openSession();
        s2.beginTransaction();

        Student stu = s2.get(Student.class, 1);
        System.out.println(stu);

        s2.getTransaction().commit();
        s2.close();
    }

    @Test
    public void current() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        //如果之前开启过session,那么会直接获取到之前的session对象
        //如果没有开启过,会打开一个新的Session
        //这样获取的Session,会在事务提交之后自动关闭
        Session cs = sf.getCurrentSession();
        cs.beginTransaction();

        Student stu = cs.get(Student.class, 1);
        System.out.println(stu);

        cs.getTransaction().commit();


        //下面代码会报错,因为session会因为事务的提交会自动关闭
        cs.beginTransaction();
        Course course = cs.get(Course.class, 1);
        System.out.println(course);

        cs.getTransaction().commit();

    }

    @Test
    public void query() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query<Course> query = session.createQuery("from Course where id>?", Course.class);
//        Query<Course> query = session.createQuery("from Course where id>:value", Course.class);
        //第一个参数代表第几个 ?
        //第二个参数代笔愛哦替换的值
        query.setParameter(0, 2);
//        query.setParameter("value", 2);

        query.list().forEach(System.out::println);
        session.getTransaction().commit();
    }
}
