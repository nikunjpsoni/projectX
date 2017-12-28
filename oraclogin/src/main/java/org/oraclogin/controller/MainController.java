package org.oraclogin.controller;

import java.security.Principal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oraclogin.model.UserInfo;
import org.oraclogin.model.UsersToken;
import org.oraclogin.service.EmailService;
import org.oraclogin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class MainController {

	@Autowired
    private UserInfoService userInfoService;
	
	@Autowired
	private EmailService emailService;
	
   @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
   public String welcomePage(Model model) {
       model.addAttribute("title", "Welcome");
       model.addAttribute("message", "This is welcome page!");
       return "welcomePage";
   }
 
   @RequestMapping(value = "/admin", method = RequestMethod.GET)
   public String adminPage(Model model) {
       return "adminPage";
   }
 
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String loginPage(Model model ) {
             return "loginPage";
   }
   
   @RequestMapping(value = "/register", method = RequestMethod.GET)
   public String registerPage(Model model ) {
             return "registerPage";
   }
   
   @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
   public String forgotPasswordPage(Model model ) {
             return "forgotPasswordPage";
   }
   
   @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
   public ModelAndView generatePasswordToken(HttpServletRequest request, HttpServletResponse response, 
   @ModelAttribute("UserInfo") UserInfo user){
	   System.out.println("email -> " + user.getEmail());
	   UserInfo userInfo = userInfoService.findUserByEmail( user.getEmail() );
	   if( userInfo != null )
	   {
		   //user found generate token and mail the link
		   String token = UUID.randomUUID().toString();
		   UsersToken userToken = new UsersToken(userInfo.getUserName(), userInfo.getEmail(), token);
		   System.out.println("Token -> " + token);
		   //save token in database
		   if(userInfoService.addPasswordToken(userToken))
		   {
			   //Token added to database now send mail
			   
			   String appUrl = request.getScheme() + "://" + request.getServerName();
				
				// Email message
				SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
				passwordResetEmail.setFrom("nikunjaadesara@gmail.com");
				passwordResetEmail.setTo(userToken.getEmail());
				passwordResetEmail.setSubject("Password Reset Request");
				passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
						+ ":8086/oraclogin/resetPassword?token=" + userToken.getToken());
				
				emailService.sendEmail(passwordResetEmail);
				
				return new ModelAndView("forgotPasswordPage", "success" ,"password recovery mail is sent to your email");
		   }
		   else
		   {
			   System.out.println("Couldn't add entry in user_token");
		   }
	   }  
	   System.out.println("Could/nt find user with email " + user.getEmail());
       return new ModelAndView("forgotPasswordPage", "error" ,"email is not registered with us"); 
   }
   
   @RequestMapping(value = "/updatePassword")
   public ModelAndView updatePassword(HttpServletRequest request)
   {
	   String un = request.getParameter("userName");
	   String password = request.getParameter("password");
	   System.out.println("un -> " + un + " password -> " + password);
	   
	   try{
		   if(userInfoService.updatePassword(un, password))
		   {
			   return new ModelAndView("updatePasswordPage", "success", "successfully password updated");
		   }
		   else
		   {
			   System.out.println("update Password failed:");
		   }
	   }
	   catch(Exception e)
	   {
		   System.out.println("Update password exception -> " + e);
	   }
	   
	   return new ModelAndView("updatePasswordPage", "error", "password updation fail with our database");
   }
   
   @RequestMapping(value = "/resetPassword")
   public ModelAndView resetPassword(HttpServletRequest request, 
		   @RequestParam("token") String token)
   {
	   try {
		   UsersToken userToken = userInfoService.findUserByToken(token);
		   if(userToken != null)
		   {
			   return new ModelAndView("updatePasswordPage", "un", userToken.getUserName());
		   }
		   else
		   {
			   System.out.println("User with token " + token + "not found. ");
		   }
	   }
	   catch(Exception e)
	   {
		   System.out.println("update password exception -> " +e);
	   }
	   return new ModelAndView("updatePasswordPage", "error", "invalid token, request again");
   }
   
   @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
   public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response,
   @ModelAttribute("UserInfo") UserInfo user) {
	   System.out.println("username -> "+user.getUserName()+ " password -> "+user.getPassword() + " email -> " + user.getEmail());
	   // TBD : add exception handling, Do not call dao directly from here
	   if(!userInfoService.save(user))
	   {
		   return new ModelAndView("registerPage","error","User Details already registered with us.");
	   }
	   return new ModelAndView("welcomePage", "message", "Welcome, "+user.getUserName());
   }
   
   @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
   public String logoutSuccessfulPage(Model model) {
       model.addAttribute("title", "Logout");
       return "logoutSuccessfulPage";
   }
 
   @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
   public String userInfo(Model model, Principal principal) {
 
       // After user login successfully.
       String userName = principal.getName();
 
       System.out.println("User Name: "+ userName);
 
       return "userInfoPage";
   }
 
   @RequestMapping(value = "/403", method = RequestMethod.GET)
   public String accessDenied(Model model, Principal principal) {
        
       if (principal != null) {
           model.addAttribute("message", "Hi " + principal.getName()
                   + "<br> You do not have permission to access this page!");
       } else {
           model.addAttribute("msg",
                   "You do not have permission to access this page!");
       }
       return "403Page";
   }
}