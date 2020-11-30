package com.proyecto.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	
	private DataSource dataSource;
	
	//Override implementation methods inicializa el tomcat por lo visto
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		// create our studen db util and pas in te conn pool /datasoirce
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// is the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			//route to the appropiated method
			switch (theCommand) {
			case "LIST":
				listStudents(request, response);
				break;
			case "ADD":
				addStudent(request, response);
				break;
			default:
				listStudents(request, response);
				break;
			}
			
			
			
			
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read studen info from data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create a new student object
		Student theStudent = new Student(firstName, lastName,email);
		
		//add the student to the database
		StudentDbUtil.addStudent(theStudent);
		
		
		// send back to main page (the student list)
		listStudents(request, response);
		
		
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) 
	throws Exception {
		// get students from db util
		List<Student> students = studentDbUtil.getStudents();
		
		// add students to the request
		request.setAttribute("STUDENT_LIST", students);
		// send to JSP 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
		
	}

}
