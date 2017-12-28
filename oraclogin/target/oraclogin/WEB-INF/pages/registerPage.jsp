<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<html>
<head><title>Register Now</title></head>
<body>
   <jsp:include page="_menu.jsp" />
    
    <!--error exist-->
     <c:if test="${not empty error}">
         <div style="color:red;margin:10px 0px;">
          
                Registration Failed!!!<br />
                Reason :  ${error}
                 
         </div>
    </c:if>
   <h1>Registration Form</h1>
                
   <h3>Enter Details:</h3>  
     
   <form name='f' action="${pageContext.request.contextPath}/saveUser" method='POST'>
      <table>
         <tr>
            <td>User:</td>
            <td><input type='text' name='userName' value=''></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td>Email:</td>
            <td><input type='text' name='email' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
  </form>
</body>
</html>