<%@page language="java" %>

<html>
<head>
    <title>Demo calculator</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <h2>Kush Calculator</h2>
    <form action="calculate" method="post">
        <label for="num1">Enter 1st Number :</label>
        <input type="text" id="num1" name="num1"><br>
        <label for="num2">Enter 2nd Number :</label>
        <input type="text" id="num2" name="num2"><br>
        <label for="operation">Select Operation :</label>
        <select name="operation" id="operation">
            <option value="add">Addition</option>
            <option value="subtract">Subtraction</option>
            <option value="multiply">Multiplication</option>
            <option value="divide">Division</option>
        </select><br>
        <input type="submit" value="Calculate">
    </form>
</body>
</html>
