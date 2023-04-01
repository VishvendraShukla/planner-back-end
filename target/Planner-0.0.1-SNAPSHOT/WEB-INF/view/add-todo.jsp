<html>
<head>
<title>ADD New ToDo</title>
   <style>
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
<div align="center">
<h1>ADD a new ToDo</h1>
<hr>
<form action="/add-todo" method="Post">
    ToDo Name<input type="text" placeholder="Enter New ToDo" name="description"><br><br>
    End Date <input type="text" size="3" placeholder="dd" name="dd" maxlength="2">
    / <input type="text" size="3" placeholder="mm" name="mm" maxlength="2">
    / <input type="text" size="5" placeholder="yyyy" name="yyyy" maxlength="4"><br>
    <input type="submit" class="button button2" value="Add Todo">
</form>
<hr>
</div>
<div align="center"> <a class="button button2" href="show-todo">Show All Todo</a></div>
</body>
</html>
