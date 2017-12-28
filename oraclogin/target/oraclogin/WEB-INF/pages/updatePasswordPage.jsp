<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<html>
<head><title>Forgot Password</title></head>
<body>
   <jsp:include page="_menu.jsp" />
    
    <!--error exist-->
     <c:if test="${not empty error}">
         <div style="color:red;margin:10px 0px;">
          
                Token invalid!!!<br />
                Reason :  ${error}
                 
         </div>
    </c:if>
       
     <!--successfull -->
     <c:if test="${not empty un}">
         <div style="color:green;margin:10px 0px;">
          
                <br />
                <h3>Welcome, ${un}</h3>
                 
         </div>
         
   <form name='f' action="${pageContext.request.contextPath}/updatePassword" method='POST'>
      <table>
         <tr>
            <td>New Password:</td>
            <td><input type='password' name='password' value=''></td>
            <td><input type="hidden" name="userName" value='${un}'></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
  </form>
         
    </c:if>
   
  
</body>
</html>