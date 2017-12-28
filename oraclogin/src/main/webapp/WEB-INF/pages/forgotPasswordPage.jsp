<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<html>
<head><title>Forgot Password</title></head>
<body>
   <jsp:include page="_menu.jsp" />
    
    <!--error exist-->
     <c:if test="${not empty error}">
         <div style="color:red;margin:10px 0px;">
          
                User Not Found!!!<br />
                Reason :  ${error}
                 
         </div>
    </c:if>
       
     <!--successfull -->
     <c:if test="${not empty success}">
         <div style="color:green;margin:10px 0px;">
          
                <br />
                <h3>${success}</h3>
                 
         </div>
    </c:if>
   <form name='f' action="${pageContext.request.contextPath}/forgotPassword" method='POST'>
      <table>
         <tr>
            <td>Email:</td>
            <td><input type='text' name='email' value=''></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
  </form>
  
</body>
</html>