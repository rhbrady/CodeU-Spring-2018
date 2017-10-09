<!DOCTYPE html>
<html>
<head>
	<title>Login</title>
	
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
</head>
<body>

<div class="container">
	<nav class="navbar navbar-default">
		<ul class="nav navbar-nav">
			<li><a href="/ChatApp">CodeU Chat App</a></li>
			<li><a href="/ChatApp/conversations">Conversations</a></li>
			<% if(request.getSession().getAttribute("user") != null){ %>
			<li><a>Hello <%= request.getSession().getAttribute("user") %>!</a></li>
			<% } else{ %>
			<li><a href="/ChatApp/login">Login</a></li>
			<% } %>
		</ul>
	</nav>
	
	<h1>Login</h1>
	
	<% if(request.getAttribute("error") != null){ %>
			<h2 style="color:red"><%= request.getAttribute("error") %></h2>
	<% } %>
	
	<form action="/ChatApp/login" method="POST">

		<div class="form-group">
			<label class="form-control-label">Name:</label>
			<input type="text" name="username" class="form-control">
		</div>
		
		<button type="submit" class="btn btn-primary">Login</button>
	</form>

</div>
		
</body>
</html>