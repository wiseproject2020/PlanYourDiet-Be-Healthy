package com.dao;

import org.hibernate.SessionFactory;

import com.ts.db.HibernateTemplate;
import com.ts.dto.Problem;
import java.util.List;

public class ProblemDAO {
	private SessionFactory factory = null;
	
	public List<Problem> getAllProblemDetails() {
		List<Problem> problem=(List)HibernateTemplate.getObjectListByQuery("From Problem");
		return problem;	
	}
	
	public int addprob(Problem problem) {
		System.out.println(problem); 
		return HibernateTemplate.addObject(problem);
	}

}
