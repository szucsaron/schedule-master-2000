<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Schedule Master 2000</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:url value="/style.css" var="styleUrl"/>
    <c:url value="/share-init.js" var="shareInitUrl"/>
    <c:url value="/index.js" var="indexScriptUrl"/>
    <c:url value="/schedule.js" var="scheduleScriptUrl"/>
    <c:url value="/date_adder.js" var="dateAdderScriptUrl"/>
    <c:url value="/new_table.js" var="tableScriptUrl"/>
    <c:url value="/auth.js" var="authScriptUrl"/>
    
    <script src="${tableScriptUrl}"></script>
    <script src="${dateAdderScriptUrl}"></script>
    <script src="${scheduleScriptUrl}"></script>
    <script src="${shareInitUrl}"></script>
    <script src="${indexScriptUrl}"></script>

    <link rel="stylesheet" type="text/css" href="${styleUrl}">
</head>
<body>
    <div id="schedule-content" class="content" schedule_id = "${scheduleId}"></div>
</body>
</html>
