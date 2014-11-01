<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload</title>
</head>
<body>
	<form method="POST" action="image" enctype="multipart/form-data">
		How much to rotate <input type="number" name="amount" /> (in radians, suggested=8)<br/>
		Select image to upload: <input type="file" name="uploadFile" />
		<br/>
		<input type="submit" value="Upload" />
	</form>
</body>
</html>