package com.ts.db;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.ts.dto.Doctors;
import com.ts.dto.Users;

public class HibernateTemplate {
	
private static SessionFactory sessionFactory;
	
     static {
    	 sessionFactory=new Configuration().configure().buildSessionFactory();
     }
	
	public static int addObject(Object obj)
	{
		//Configuration config = new Configuration();
		//config.configure("hibernate.cfg.xml");
		//SessionFactory factory = config.buildSessionFactory();
		System.out.println("Inside Template...");
		int result=0;
		
		Transaction tx=null;
		
		try {
			
			Session session=sessionFactory.openSession();
			tx=session.beginTransaction();
			session.save(obj);
			tx.commit();
			
			/*Session session = factory.openSession();
			System.out.println("Inside Template...");
			session.save(obj); //insert query
			tx = session.beginTransaction();
			tx.commit(); //permanent save
			session.close();*/
			
			result=1;
			
		} catch (Exception e) {
		
			tx.rollback();
			
			e.printStackTrace();
		}
		
		return result;
	}
	public static Object getObjectByUserPass(String username,String password) {
		
		String queryString = "from Users where username = :username and password =:password";
		  Query query = sessionFactory.openSession().createQuery(queryString);
		  query.setString("username", username);
		  query.setString("password", password);
		  Object queryResult = query.uniqueResult();
		  Users user = (Users)queryResult;
		  return user; 
	}
public static Object getObjectByDoctorPass(String doctorName,String doctorPassword) {
		
		String queryString = "from Doctors where doctorName = :doctorName and doctorPassword =:doctorPassword";
		  Query query = sessionFactory.openSession().createQuery(queryString);
		  query.setString("doctorName", doctorName);
		  query.setString("doctorPassword", doctorPassword);
		  Object queryResult = query.uniqueResult();
		  Doctors doctor = (Doctors)queryResult;
		  return doctor; 
	}
public static Object getObjectByEmail(String username) {

	String queryString = "from Users where username=:username";
	 Query query = sessionFactory.openSession().createQuery(queryString);
	 query.setString("username", username);
	 Object queryResult = query.uniqueResult();
	 Users user = (Users)queryResult;
	 return user;
	}
public static List<Object> getObjectListByQuery(String query)
{
	return sessionFactory.openSession().createQuery(query).list();
}
public static Users getObjectByauthEmail(String userEmail) {
	String queryString = "from Users where userEmail=:userEmail";
	 Query query = sessionFactory.openSession().createQuery(queryString);
	 query.setString("userEmail", userEmail);
	 Object queryResult = query.uniqueResult();
	 Users user = (Users)queryResult;
	 return user;
}
}
