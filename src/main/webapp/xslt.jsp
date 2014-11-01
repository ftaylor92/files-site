<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload</title>
</head>
<body>
	<form method="POST" action="xml" enctype="multipart/form-data">
		XSL File: <input type="file" name="xslFile" /><br/>
		XML File to Transform with XSL: <input type="file" name="xmlFile" />
		<br/>
		<input type="submit" value="Upload" />
	</form>
</body>
</html>