<!DOCTYPE html>
<html>
<head>
	<title>CodeU Chat App</title>
	
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
	
	
	
	<div class="jumbotron" style="width:75%; margin-left:auto; margin-right:auto;">
	
	<h1>CodeU Chat App</h1>
	
	<p>Welcome to the CodeU Chat App! This is an example chat application designed to make it easy
		to improve and build on. Here's some stuff to think about:</p>
		
	<ul>
		<li><strong>Algorithms and data structures:</strong> We tried to keep the code as simple
			as possible, but that comes at the cost of poor performance. Can you improve that?</li>
		<li><strong>Look and feel:</strong> The focus of CodeU is on the Java side of things,
			but if you're particularly interested you might use HTML, CSS, and JavaScript to
			make the chat app prettier.</li>
		<li><strong>Customization:</strong> Think about a group you care about. What needs do they
			have? How could you help? Think about technical consideration, privacy concerns, and
			accessibility and internationalization.</li>
	</ul>
	<p><a href="/ChatApp/login">Login</a> to get started, then go to the <a href="/ChatApp/conversations">conversations</a>
		page to create or join a conversation.</p>
	
	</div>
	
	
	
	
	
</div>
		
</body>
</html>