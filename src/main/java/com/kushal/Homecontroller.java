package com.kushal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;

@Controller
public class Homecontroller {

	
	@RequestMapping("/")
	public String Home() {
		System.out.println("Home page is called");
		return "index";
	}
	
	@RequestMapping("calculate")
	/*public String add(HttpServletRequest req ,HttpSession session) {
		
		int num1=Integer.parseInt(req.getParameter("num1")); 
		int num2=Integer.parseInt(req.getParameter("num2"));
		
		int result=num1+num2;
		session.setAttribute("result",result); */
	public String add(@RequestParam("num1") int num1,@RequestParam("num2")int num2,@RequestParam("operation")String operation,Model model) {
		int result=0;
		switch (operation) {
        case "add":
            result = num1 + num2;
            break;
        case "subtract":
            result = num1 - num2;
            break;
        case "multiply":
            result = num1 * num2;
            break;
        case "divide":
            if (num2 != 0) {
                result = num1 / num2;
            } else {
                result = 0; 
            }
            break;
        default:
            result = 0; 
            break;
    }
		
		
		
		model.addAttribute("result",result);
		System.out.println(result);

		
		
		
		return "result";
	}
}
