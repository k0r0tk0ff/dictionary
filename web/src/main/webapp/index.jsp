<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<jsp:directive.page contentType="text/html; charset=UTF-8" />--%>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" name="viewport" content="width=device-width, initial-scale=1">
   <%-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <script src="<c:url value="/bootstrap/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
</head>
<body>

<div class="container">
    <h1>Dictionary</h1>
    <form id="translateform" action="MainServlet" method="post" class="form-horizontal" role="form">
        <div style="margin-bottom: 25px" class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
            <input id="wordForTranslate"
                   type="wordForTranslate"
                   class="form-control"
                   name="wordForTranslate"
                   placeholder="wordForTranslate">
        </div>
        <div style="margin-top:10px" class="form-group">
            <div class="col-sm-12 controls">
                <input type="submit" class="btn btn-default pull-right" value="Translate">
            </div>
        </div>

        <div class="panel panel-info">
            <div class="panel-heading">Translated word</div>
            <div class="panel-body"><c:out value="${result}"/></div>
        </div>

        <div class="panel panel-info">
            <div class="panel-heading">Русские буквы</div>
            <div class="panel-body"><c:out value="${russ}"/></div>
        </div>
    </form>
</div>

</body>
</html>