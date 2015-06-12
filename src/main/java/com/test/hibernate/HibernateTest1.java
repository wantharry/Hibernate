package com.test.hibernate;

import com.test.domain.PersonalCallCount;
import com.test.util.HibernateUtil1;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class HibernateTest1 {
    private static final Logger logger = LoggerFactory.getLogger(HibernateTest1.class);

    public static void main(String[] args) {
        logger.info("Enter the Personal ID : ");
        Scanner sc = new Scanner(System.in);
        PersonalCallCount newPerson;
        long id = sc.nextLong();

        Session session = HibernateUtil1.getSessionFactory().openSession();
        session.beginTransaction();
        PersonalCallCount existingPerson = (PersonalCallCount) session.get("com.test.domain.PersonalCallCount", id);
        if (existingPerson == null) {
            logger.info("Employee Calling for the first time");
            newPerson = new PersonalCallCount(id, new Long(1), new Date());
            logger.info("New Person : " + String.valueOf(newPerson));
            session.save(newPerson);
        } else {
            long count = existingPerson.getCount();
            existingPerson.setCount(++count);
            logger.info("Info of request person " + existingPerson);
            existingPerson.setDate(new Date());
            session.save(existingPerson);
        }

        session.getTransaction().commit();

        Query query = session.createQuery("From PersonalCallCount ");
        List<PersonalCallCount> resultList = query.list();
        System.out.println("Number of Employees :- " + resultList.size());
        logger.info("---------------------------------------------------");
        for (PersonalCallCount next : resultList) {
            logger.info("Employee List: " + next);
        }

        session.close();
    }
}