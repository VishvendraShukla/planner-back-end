<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>TODO</title>
   <style>
   div,table{
   width: 100%;
   }
   tr{
   	text-align: center;
   }
   .button {
     background-color: #4CAF50; /* Green */
     border: none;
     color: white;
     padding: 16px 32px;
     text-align: center;
     text-decoration: none;
     display: inline-block;
     font-size: 16px;
     margin: 4px 2px;
     transition-duration: 0.4s;
     cursor: pointer;
   }
    .button2 {
      background-color: white;
      color: #008CBA;
      border: 2px solid #008CBA;
    }

    .button2:hover {
      background-color: #008CBA;
      color: white;
    }
   </style>
</head>
<body>
<br>
<br>
<br>
<div><a class="button" href="add-todo">Add TODO</a></div>
<div align="center">
<table border="border" align="center">
    <tr>
        <th>Description</th>
        <th>Last Date</th>
        <th>action</th>
    </tr>
    <c:forEach var="i" items="${useMe}">
        <tr>
            <td>${i.description}</td>
            <td>${i.tillDate}</td>
            <td><a class="button" href="/update-todo/{i.id}">Update</a><a class="button"type="submit" href="/delete-todo?id=${i.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>


</div>

</body>
</html>