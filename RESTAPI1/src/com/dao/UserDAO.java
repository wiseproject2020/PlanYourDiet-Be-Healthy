package com.dao;

import org.hibernate.SessionFactory;

import com.ts.db.HibernateTemplate;
import com.ts.dto.Users;

public class UserDAO {
	private SessionFactory factory = null;

	public Users getUserByUserPass(String username,String password) {

		return (Users)HibernateTemplate.getObjectByUserPass(username,password);
	}
	public int register(Users user) {
		System.out.println(user); 
		return HibernateTemplate.addObject(user);
	}
	public Users getUsersByEmail(String username) {
		// TODO Auto-generated method stub
		System.out.println("DAO "+username);
		return (Users)HibernateTemplate.getObjectByEmail(username);
	}
	public Users authEmail(String userEmail) {
		// TODO Auto-generated method stub
		System.out.println("DAO "+userEmail);
		return (Users)HibernateTemplate.getObjectByauthEmail(userEmail);
	}

}
