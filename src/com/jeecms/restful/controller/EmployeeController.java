package com.jeecms.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.jeecms.restful.bean.Employee;

//http://www.xdemo.org/spring-restful/
//http://www.2cto.com/kf/201109/105524.html
//http://my.oschina.net/yao00jun/blog/223495
//http://blog.arganzheng.me/posts/restful-springmvc.html
//https://github.com/gefangshuai/base-mvc/zipball/master
@Controller
public class EmployeeController {

 

	private static final String XML_VIEW_NAME = "findJsp";
	
	@RequestMapping(method=RequestMethod.GET, value="/employee/{id}")
	public ModelAndView getEmployee(@PathVariable String id) {
		Employee e =new Employee(1,"name","dadada@qq.com"); //employeeDS.get(Long.parseLong(id));
		return new ModelAndView(XML_VIEW_NAME, "object", e);
	}
	
//	@RequestMapping(method=RequestMethod.PUT, value="/employee/{id}")
//	public ModelAndView updateEmployee(@RequestBody String body) {
//		Source source = new StreamSource(new StringReader(body));
//		Employee e = (Employee) jaxb2Mashaller.unmarshal(source);
//		employeeDS.update(e);
//		return new ModelAndView(XML_VIEW_NAME, "object", e);
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value="/employee")
//	public ModelAndView addEmployee(@RequestBody String body) {
//		Source source = new StreamSource(new StringReader(body));
//		Employee e = (Employee) jaxb2Mashaller.unmarshal(source);
//		employeeDS.add(e);
//		return new ModelAndView(XML_VIEW_NAME, "object", e);
//	}
//	
//	@RequestMapping(method=RequestMethod.DELETE, value="/employee/{id}")
//	public ModelAndView removeEmployee(@PathVariable String id) {
//		employeeDS.remove(Long.parseLong(id));
//		List<Employee> employees = employeeDS.getAll();
//		EmployeeList list = new EmployeeList(employees);
//		return new ModelAndView(XML_VIEW_NAME, "employees", list);
//	}
//	
//	@RequestMapping(method=RequestMethod.GET, value="/employees")
//	public ModelAndView getEmployees() {
//		List<Employee> employees = employeeDS.getAll();
//		EmployeeList list = new EmployeeList(employees);
//		return new ModelAndView(XML_VIEW_NAME, "employees", list);
//	}
//	
}
