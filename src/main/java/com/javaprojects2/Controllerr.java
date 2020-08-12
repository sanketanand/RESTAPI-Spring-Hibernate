package com.javaprojects2;

//import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableWebMvc
public class Controllerr {

//	ApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
//	
//	DAO dao=context.getBean(DAO.class);
//	
//	Person person=context.getBean(Person.class);
	
	@Autowired
	private DAO dao;
	
	@Autowired
	private Person person;
	
	private SessionFactory sf;

	@RequestMapping("/addrecord")
	public String function(HttpServletRequest request , HttpServletResponse response) {
		
		person.setId(Integer.parseInt(request.getParameter("id")));
		person.setName(request.getParameter("name"));
		person.setAge(Integer.parseInt(request.getParameter("age")));
		
		
		sf = dao.createSessionFactory();
		
		Session session=sf.openSession();
		Transaction tx=session.beginTransaction();
		
		session.save(person);
		
		tx.commit();
		
		session.close();
		
		return person.toString();
	}
	
	@RequestMapping("/getrecord")
	public String function2(HttpServletRequest request , HttpServletResponse response) {
		int i=Integer.parseInt(request.getParameter("id"));
		
		sf=dao.createSessionFactory();
		
		Session session=sf.openSession();
		Transaction tx=session.beginTransaction();
		
		Query q=session.createQuery("from Person where ID = :i");
		q.setParameter("i" , i);
		
		person=(Person) q.uniqueResult();
		tx.commit();
		return person.toString();
	
	}
	
	@GetMapping(value  = "/getbyid/{aid}" , produces = "application/json")
	public Person getvaluebyID(@PathVariable("aid") int aid) {
		
		sf=dao.createSessionFactory();
		
		Session session=sf.openSession();
		Transaction tx=session.beginTransaction();
		
		Query q=session.createQuery("from Person where ID = :aid");
		q.setParameter("aid" , aid);
		
		person=(Person) q.uniqueResult();
		tx.commit();
		return person;
	}
	
	@PostMapping("/person")
	public Person add(@RequestBody Person person) {
		
		sf = dao.createSessionFactory();
		
		Session session=sf.openSession();
		Transaction tx=session.beginTransaction();
		
		session.save(person);
		
		tx.commit();
		
		session.close();
		
		return person;
	}
	
	@GetMapping(path  = "/allrecords" , produces = "application/json")
	public List<Person> getAll() {
		
		sf = dao.createSessionFactory();
		
		Session session=sf.openSession();
		
		Query q = session.createQuery("from Person");
		
		return q.list();
	}
	
	@DeleteMapping(path = "/deletebyid/{aid}")
	public String deleterecord(@PathVariable int aid) {
		
		sf=dao.createSessionFactory();
		
		Session session=sf.openSession();
		Transaction tx=session.beginTransaction();
		
		Query q=session.createQuery("delete from Person where ID = :aid");
		q.setParameter("aid" , aid);
		
		tx.commit();
		
		return "deleted the data";
	}
}
