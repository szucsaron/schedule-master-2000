<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Schedule Master 2000</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:url value="/style.css" var="styleUrl"/>
    <c:url value="/index.js" var="indexScriptUrl"/>
    <c:url value="/login.js" var="loginScriptUrl"/>
    <c:url value="/logout.js" var="logoutScriptUrl"/>
    <c:url value="/welcome.js" var="welcomeScriptUrl"/>
    <c:url value="/register.js" var="registerScriptUrl"/>
    <c:url value="/task.js" var="taskScriptUrl"/>
    <c:url value="/tasks.js" var="tasksScriptUrl"/>
    <c:url value="/schedule.js" var="scheduleScriptUrl"/>
    <c:url value="/schedules.js" var="schedulesScriptUrl"/>
    <c:url value="/date_adder.js" var="dateAdderScriptUrl"/>
    <c:url value="/new_table.js" var="tableScriptUrl"/>
    <script src="${tableScriptUrl}"></script>
    <script src="${dateAdderScriptUrl}"></script>
    <script src="${taskScriptUrl}"></script>
    <script src="${tasksScriptUrl}"></script>
    <script src="${scheduleScriptUrl}"></script>
    <script src="${schedulesScriptUrl}"></script>
    <script src="${indexScriptUrl}"></script>
    <script src="${loginScriptUrl}"></script>
    <script src="${welcomeScriptUrl}"></script>
    <script src="${logoutScriptUrl}"></script>
    <script src="${registerScriptUrl}"></script>
    <link rel="stylesheet" type="text/css" href="${styleUrl}">
</head>
<body>
    <div id="login-content" class="content">
        <h1>Login</h1>
        <form id="login-form" onsubmit="return false;">
            <input type="text" name="email" placeholder="Email">
            <input type="password" name="password" placeholder="Password">
            <button id="login-button">Login</button>
            <button id="register-button">Register</button>
        </form>
    </div>
    <div id="register-content" class="hidden content">
        <h1>Register</h1>
        <form id="register-form" onsubmit="return false;">
            <input type="text" name="email" placeholder="Email">
            <br>
            <input type="password" name="password" placeholder="Password">
            <br>
            <input type="password" name="repassword" placeholder="Re-enter password">
            <br>
            <input type="text" name="name" placeholder="Name">
            <br>
            <button id="registration-button">Register</button>
        </form>
    </div>
    <div id="profile-content" class="hidden content">
        <h1>Profile</h1>
        <p>Email: <span id="user-email"></span></p>
        <p>Password: <span id="user-password"></span></p>
        <li><a href="javascript:void(0);" onclick="onSchedulesClicked();">Schedules</a></li>
        <li><a href="javascript:void(0);" onclick="onTasksClicked();">Tasks</a></li>
    </div>
    <div id="back-to-profile-content" class="hidden content">
        <button onclick="onBackToProfileClicked();">Back to profile</button>
    </div>
    <div id="logout-content" class="hidden content">
        <button id="logout-button">Logout</button>
    </div>
    <div id="tasks-content" class="hidden content">
        <h1>Your tasks</h1>
        <table id="tasks">
            <thead>
            <tr>
                <th>Title</th>
                <th>Content</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <h2>Add new task</h2>
        <form id="task-form" onsubmit="return false;">
            <input type="text" name="name">
            <input type="text" name="content">
            <button onclick="onTaskAddClicked();">Add</button>
        </form>
    </div>
    <div id="task-content" class="hidden content">
        <h1>Task</h1>
        <p>Title: <span id="task-title"></span></p>
        <p>Content: <span id="task-text"></span></p>
        <p>Schedules: <span id="task-schedules"></span></p>
        <h2>Add to schedules</h2>
        <form id="task-schedules-form" onsubmit="return false">
            <select name="schedules" multiple>
            </select>
            <button onclick="onTaskSchedulesAddClicked();">Add</button>
            <button id="task-back-to-schedule" onclick="onTaskBackToScheduleClicked();">Back to Schedule</button>
        </form>
    </div>
    <div id="schedules-content" class="hidden content">
        <h1>Your schedules</h1>
        <table id="schedules">
            <thead>
            <tr>
                <th>Name</th>
                <th>Starting date</th>
                <th>Finishing date</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <h2>Add new schedule</h2>
        <form id="schedule-form" onsubmit="return false;">
            <input type="text" name="name">
            <input type="datetime-local" name="starting">
            <input type="datetime-local" name="finishing">
            <button onclick="onScheduleAddClicked();">Add</button>
        </form>
    </div>
    <div id="schedule-content" class="hidden content"></div>
    <div id="bin" class="hidden content">
        <img src="bin.png">
    </div>
    <div id="toolbox" class="hidden content">
        <img src="toolbox2.png">
    </div>
    <div id="pass" class="content"></div>
</body>
</html>
