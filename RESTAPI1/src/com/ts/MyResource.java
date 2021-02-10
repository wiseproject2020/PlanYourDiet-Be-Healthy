package com.ts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.ts.dto.Doctors;
import com.ts.dto.Problem;
import com.ts.dto.Users;
import com.dao.DoctorDAO;
import com.dao.ProblemDAO;
import com.dao.UserDAO;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @Path("registerUsers")
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String registerUsers(Users user) {
 
		System.out.println("Data Recieved in User Register : " + user);
		UserDAO userDao = new UserDAO();
		userDao.register(user);
		return "sucess";
	}
    
    @Path("registerDoctors")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String registerDoctors(Doctors doctor) {
    	
		System.out.println("Data Recieved in Doctor Register : " + doctor);
		DoctorDAO doctorDao = new DoctorDAO();
		doctorDao.register(doctor);
		return "sucess";
	}
    
//    @Path("getUserByUserPass/{username}/{password}")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Users getUserByUserPass(@PathParam("username") String username,@PathParam("password") String password) {
//		System.out.println("Recieved path params: "+username+" "+password); 
//		UserDAO userDAO = new UserDAO();
//		Users user = userDAO.getUserByUserPass(username, password);
//		return user;
//	}
    
    @Path("getDoctorByUserPass/{doctorName}/{doctorPassword}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Doctors getDoctorByUserPass(@PathParam("doctorName") String doctorName,@PathParam("doctorPassword") String doctorPassword) {
		System.out.println("Recieved path params: "+doctorName+" "+doctorPassword); 
		DoctorDAO doctorDAO = new DoctorDAO();
		Doctors doctor = doctorDAO.getDoctorByUserPass(doctorName, doctorPassword);
		return doctor;
	}
    
    @Path("getUsersByEmail/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Users getUsersByEmail(@PathParam("username") String username) {
    System.out.println("Recieved path params: "+username);
    UserDAO userDAO = new UserDAO();
    Users user = userDAO.getUsersByEmail(username);
    return user;
    }
    
    @Path("authEmail/{userEmail}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Users authEmail(@PathParam("userEmail") String userEmail) {
    System.out.println("Recieved path params: "+userEmail);
    UserDAO userDAO = new UserDAO();
    Users user = userDAO.authEmail(userEmail);
    return user;
    }
    
    @Path("uploadImage")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadImage(@FormDataParam("probimg") InputStream fileInputStream,@FormDataParam("probimg") FormDataContentDisposition
	formDataContentDisposition, @FormDataParam("probid") String probid, @FormDataParam("probname") String probname) throws IOException {; 
		int read = 0;
		byte[] bytes = new byte[1024];
		
		String path = this.getClass().getClassLoader().getResource("").getPath();
		
		String pathArr[] = path.split("/WEB-INF/classes/");
		
		FileOutputStream out = new FileOutputStream(new File(pathArr[0]+"/image/", formDataContentDisposition.getFileName()));
				
				
		while((read = fileInputStream.read(bytes)) != -1){	
			
			out.write(bytes,0,read);
		}
		out.flush();
		out.close();
		
		Problem prob = new Problem();
		prob.setProbid(probid);
		prob.setProbname(probname);
		prob.setProbimg(formDataContentDisposition.getFileName());
		ProblemDAO probDao = new ProblemDAO();
		probDao.addprob(prob);
	}
	@Path("getProblem")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Problem> getProblem() {

		ProblemDAO probDAO = new ProblemDAO();
		List <Problem> probList = probDAO.getAllProblemDetails();

		return probList;
	}
	@Path("mail/{emailId}/{body}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String mail(@PathParam("emailId") String emailId,@PathParam("body") String body1) throws MessagingException {
		String subject="From Plan-Your-Diet..";
		String body=body1;
		String email = emailId;
		String host = "smtp.gmail.com";
		String from = "tejaswi15208@gmail.com";
		String pass = "kotha@15208";

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true"); // added this line
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		String[] to = {email}; // added this line
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));

		InternetAddress[] toAddress = new InternetAddress[to.length];
		// To get the array of addresses

		for( int i=0; i < to.length; i++ )
		{
			// changed from a while loop
			toAddress[i] = new InternetAddress(to[i]);
		}
		for( int i=0; i < toAddress.length; i++)
		{
			// changed from a while loop
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}

		message.setSubject(subject);
		message.setText(body);

		Transport transport = session.getTransport("smtp");
		transport.connect(host, from, pass);
		transport.sendMessage(message, message.getAllRecipients());
		
		transport.close();

		return "Successful";
	}
}
