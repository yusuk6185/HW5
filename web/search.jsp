<%-- 
    Document   : search
    Created on : 2018. 4. 17, 오후 9:31:01
    Author     : Ian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
        <title>Search Barcasquad</title>
    </head>
    <body>
        <h1>Search Players</h1>

        <form name="searchForm" action="search" method="get">

            <input type="text" name="searchVal" value="" />

            <br>

            <input type="submit" name="submit" value="Search" />
        </form>
    </body>
</html>
