package com.test.hibernate;

import com.test.domain.Department;
import com.test.domain.Employee;
import com.test.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class HibernateTest {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Department department = new Department("java");
        session.save(department);
        session.save(new Employee("Harry Potter", department));
        session.save(new Employee("New Name", department));
        //session.save(new PersonalCallCount(1,0));
        session.getTransaction().commit();

        Query q = session.createQuery("From Employee ");
        List<Employee> resultList = q.list();
        System.out.println("num of employess:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
        session.close();
    }
}