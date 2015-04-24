<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Assignment01</title>
	</head>
	<body>
		<c:choose>
			<c:when test="${user != null}">
				<p>
					Welcome ${user.email} <br/>
					<a href="${logout}">Log out</a>
				</p>
				
				<p>Search for anagrams</p>
				<form action="/anagram" method="get">
					Please enter the word: <input type="text" name="wordSearch"/><br/>
					<input type="submit" value="Search" />
				</form>
				
				<p>
					${search_list}
				</p>
				
				<p>Add word to dictionary</p>
				<form action="/anagram" method="post">
					Please enter the word you want to add: <input type="text" name="wordAdd"/><br/>
					<input type="submit" value="Search" />
				</form>
				
				<p>
					${search}
				</p>
			</c:when>
			<c:otherwise>
				<p>
					Welcome
					Please <a href="${login}"> log in</a>
				</p>
			</c:otherwise>
		</c:choose>
		
	</body>
</html>