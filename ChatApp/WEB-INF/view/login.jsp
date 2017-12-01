<!DOCTYPE html>
<html>
<head>
	<title>Login</title>
	<link rel="stylesheet" href="/ChatApp/css/main.css">
</head>
<body>

	<nav>
		<a id="navTitle" href="/ChatApp">CodeU Chat App</a>
		<a href="/ChatApp/conversations">Conversations</a>
		<% if(request.getSession().getAttribute("user") != null){ %>
			<a>Hello <%= request.getSession().getAttribute("user") %>!</a>
		<% } else{ %>
			<a href="/ChatApp/login">Login</a>
		<% } %>
	</nav>

	<div id="container">
		<h1>Login</h1>

		<% if(request.getAttribute("error") != null){ %>
				<h2 style="color:red"><%= request.getAttribute("error") %></h2>
		<% } %>

		<form action="/ChatApp/login" method="POST">
			<label for="username">Username: </label>
			<input type="text" name="username" id="username">
			<button type="submit">Login</button>
		</form>
	</div>	
</body>
</html>
