package com.dao;

import com.ts.db.HibernateTemplate;
import com.ts.dto.Doctors;

public class DoctorDAO {

	public int register(Doctors doctor) {
		System.out.println(doctor); 
		return HibernateTemplate.addObject(doctor);
	}
	public Doctors getDoctorByUserPass(String doctorName,String doctorPassword) {

		return (Doctors)HibernateTemplate.getObjectByDoctorPass(doctorName,doctorPassword);
	}
}
