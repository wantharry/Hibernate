package com.test.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.domain.PersonalCallCount;
import com.test.util.HibernateUtil;
import com.test.util.HibernateUtil1;

public class TestCache {

	private static final Logger logger = LoggerFactory
			.getLogger(HibernateTest1.class);

	public static void main(String[] args) throws InterruptedException, ParseException {
	//	listPersonalCallCount();

         /*  CriteriaImpl c;
		SessionFactoryImplementor factory = (SessionFactoryImplementor) HibernateUtil.getSessionFactory();
		String[] implementors = factory.getImplementors( c.getEntityOrClassName() );
		CriteriaLoader loader = new CriteriaLoader((OuterJoinLoadable)factory.getEntityPersister(implementors[0]),
		    factory, c, implementors[0], s.getEnabledFilters());
		Field f = OuterJoinLoader.class.getDeclaredField("sql");
		f.setAccessible(true);
		String sql = (String)f.get(loader);*/

		Session session1 = HibernateUtil1.getSessionFactory().openSession();
		session1.beginTransaction();
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date dateWithoutTime = sdf.parse(sdf.format(new Date()));

	    //method 2
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    dateWithoutTime = cal.getTime();*/
		Date date = new Date();


		/*PersonalCallCount existingPerson1 = (PersonalCallCount) session1.get(
				"com.test.domain.PersonalCallCount", new Long(1));*/

		List<PersonalCallCount>listOfPersons1 =session1.createCriteria("com.test.domain.PersonalCallCount")
				.add(Restrictions.ge("date", date))
				//.add(Restrictions.le("id", new Long(3)))
		.setCacheable(true)
		.list()
		;

		logger.info("date1 "+date);
		logger.info("Size of List1 "+listOfPersons1.size());
		session1.getTransaction().commit();
		session1.close();

		Thread.sleep(3000);
		//logger.info(existingPerson1.toString());
		Session session2 = HibernateUtil1.getSessionFactory().openSession();
		session2.beginTransaction();
		Date date2 = new Date();
	/*	PersonalCallCount existingPerson2 = (PersonalCallCount) session2.get(
				"com.test.domain.PersonalCallCount", new Long(1));*/
		List<PersonalCallCount>listOfPersons2 =session2.createCriteria("com.test.domain.PersonalCallCount")
				.add(Restrictions.ge("date", date2))
				//.add(Restrictions.le("id", new Long(3)))
		.setCacheable(true)
		.list()
		;
		logger.info("date2 "+date2);
		session2.getTransaction().commit();
		logger.info("Size of List2 "+listOfPersons2.size());
		//logger.info(existingPerson2.toString());
		session2.close();
	}

	private static void listPersonalCallCount() {
		logger.info("Enter the Personal ID : ");
		Scanner sc = new Scanner(System.in);
		PersonalCallCount newPerson;
		long id = sc.nextLong();

		Session session = HibernateUtil1.getSessionFactory().openSession();
		session.beginTransaction();
		PersonalCallCount existingPerson = (PersonalCallCount) session.get(
				"com.test.domain.PersonalCallCount", id);
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