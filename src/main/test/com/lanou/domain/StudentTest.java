package com.lanou.domain;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.HibernateUtil;

import static org.junit.Assert.*;

/**
 * Created by dllo on 17/11/1.
 */
public class StudentTest {
    private Session session;

    //单向多对多
    //可以查出某个学生学习了哪些课程,但是不能查出某个课程有哪些学生学习了

    @Before
    public void init(){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
    }

    @After
    public void destory(){
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void save(){
        Student s1 = new Student("东少");
        Student s2 = new Student("靖靖");

        Course c1 = new Course("语文");
        Course c2 = new Course("数学");
        Course c3 = new Course("英语");

        s1.getCourses().add(c1);
        s1.getCourses().add(c2);

        s2.getCourses().add(c1);
        s2.getCourses().add(c2);
        s2.getCourses().add(c3);

        session.save(s1);
        session.save(s2);
    }


    @Test
    public void query(){
        Student student = session.get(Student.class, 1);
        System.out.println(student);
        System.out.println(student.getCourses());

        //观察log,如果只查询了一次数据库.说明第二次取出的数据不是从数据库中查询出来的
        //用来验证Hibernate的一级缓存
        //一级缓存的生命周期是随着session变化的,如果session关闭,那么与之对应的缓存也会被清空
        //如果再次打开session,那么会创建出新的缓存
        session.getTransaction().commit();
        session.close();
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Student student1 = session.get(Student.class, 1);
        System.out.println(student.hashCode());
        System.out.println(student1.hashCode());

    }
}