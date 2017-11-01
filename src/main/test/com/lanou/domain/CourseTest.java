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
public class CourseTest {

    private Session session;

    //测试多对多关联
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
        //多对多双向报讯数据
        Student s1 = new Student("海伦");
        Student s2 = new Student("东东");

        Course c1 = new Course("Servlet");
        Course c2 = new Course("Struts2");
        Course c3 = new Course("Hibernate");


        c1.getStudents().add(s1);
        c1.getStudents().add(s2);

        c2.getStudents().add(s1);

        c3.getStudents().add(s1);
        c3.getStudents().add(s2);

        session.save(c1);
        session.save(c2);
        session.save(c3);
//        s1.getCourses().add(c1);
//        s1.getCourses().add(c2);
//
//        s2.getCourses().add(c1);
//        s2.getCourses().add(c2);
//        s2.getCourses().add(c3);
//
//        session.save(s1);
//        session.save(s2);

    }

    @Test
    public void query(){
        //测试双向多对多查询
        Student student = session.get(Student.class, 1);
        System.out.println(student);
        System.out.println(student.getCourses());


        Course course = session.get(Course.class, 2);
        System.out.println(course);
        System.out.println(course.getStudents());
    }
}