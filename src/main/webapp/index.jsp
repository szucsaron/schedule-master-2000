<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id" content="59889217720-mkvqq0odl79dop79as4ivko6lsovk3fq.apps.googleusercontent.com">

    <title>Schedule Master 2000</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <c:url value="/style.css" var="styleUrl"/>
    <c:url value="/normal-init.js" var="normalInitUrl"/>
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
    <c:url value="/auth.js" var="authScriptUrl"/>
    <c:url value="/draggable.js" var="draggableUrl"/>
    <c:url value="/admin.js" var="adminUrl"/>



    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="${tableScriptUrl}"></script>
    <script src="${dateAdderScriptUrl}"></script>
    <script src="${taskScriptUrl}"></script>
    <script src="${tasksScriptUrl}"></script>
    <script src="${scheduleScriptUrl}"></script>
    <script src="${schedulesScriptUrl}"></script>
    <script src="${normalInitUrl}"></script>
    <script src="${indexScriptUrl}"></script>
    <script src="${loginScriptUrl}"></script>
    <script src="${welcomeScriptUrl}"></script>
    <script src="${logoutScriptUrl}"></script>
    <script src="${registerScriptUrl}"></script>
    <script src="${draggableUrl}"></script>
    <script src="${adminUrl}"></script>
    <link rel="stylesheet" type="text/css" href="${styleUrl}">
</head>
<body>
    <div id="user-menu" class="content">
        <table>
            <tr id= user-menu-tr>
                <td><a href="javascript:void(0);" onclick="onHomepageClicked();">Homepage</a></td> 
                <td><a href="javascript:void(0);" onclick="onSchedulesClicked();">Schedules</a></td> 
                <td><a href="javascript:void(0);" onclick="onTasksClicked();">Tasks</a></td>  
                <td id="logout-td"><a href="javascript:void(0);" id="logout-button">Logout </a></td>
        </table>  
    </div>
    <div id="login-content" class="hidden content">
        <h1>Login</h1>
        <form id="login-form" onsubmit="return false;">
            <input type="text" name="email" placeholder="Email">
            <input type="password" name="password" placeholder="Password">
            <button id="login-button">Login</button>
            <button id="register-button">Register</button>
            <div class="g-signin2" data-onsuccess="onGoogleSignIn">
            </div>
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
        <div class="g-signin2" data-onsuccess="onGoogleRegister"></div>
    </div>
    <div id="tasks-content" class="hidden content">
        <h1>Your tasks</h1>
        <form id="tasks-delete-form" onsubmit="return false">
            <table id="tasks">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Content</th>
                    <th>Edit</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <button onclick="onTasksDeleteClicked();">Delete</button>
        </form>

        <h2>Add new task</h2>
        <form id="task-form" onsubmit="return false;">
            <input type="text" name="name" placeholder="Name">
            <input type="text" name="content" placeholder="Description">
            <button onclick="onTaskAddClicked();">Add</button>
        </form>
    </div>
    <div id="task-content" class="hidden content">
            <h2>Title</h2>
            <input type="hidden" name="id" value="" id="hidden-id" data-task-id = "">
            <input type="text" name="title" id="task-title">
            <br>
            <h2>Content</h2>
            <textarea rows="10" cols="30" type="text" name="content" id="task-text"></textarea>
            <h2>Schedules:</h2>
                <span id="task-schedules"></span>
            <button onclick="onTaskUpdate();" id="update">Update</button>
            <button id="task-back-to-schedule" onclick="onTaskBackToScheduleClicked();">Back to Schedule</button>
        </form>
    </div>
    <div id="public-schedules" class="hidden content">
        <h1 id='public-schedules-h1'></h1>
        <table id="pubschedules">
            <thead>
            <tr>
                <th>Name</th>
                <th>Starting date</th>
                <th>Finishing date</th>
                <th>Created by</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div id="users-content" class="hidden content">
        <h1>Registered users</h1>
        <table id="users-table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div id="schedules-content" class="hidden content">
        <h1>Your schedules</h1>
        <div id="public-schedule-link" class = "hidden">
            <button id="public-schedule-link-close"  onClick="onPublicScheduleLinkCloseClicked();">
                x
            </button>
            Link:
            <div id = "public-schedule-link-txt"></div>
        </div>
        <form id="schedules-delete-form" onsubmit="return false">
            <table id="schedules">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Starting date</th>
                    <th>Finishing date</th>
                    <th>Share schedule</th>
                    <th>Edit</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <button onclick="onSchedulesDeleteClicked();">Delete</button>
            <button onclick="onSchedulesUpdateClicked();">Update</button>
        </form>
        <h2>Add new schedule</h2>
        <form id="schedule-form" onsubmit="return false;">
            <input type="text" name="name" placeholder="Name">
            <input type="date" name="starting-date" placeholder="Date">
            <input type="text" name="schedule-duration" placeholder="Duration in days">
            <button onclick="onScheduleAddClicked();">Add</button>
        </form>
    </div>
    <div id="schedule-content" class="hidden content"></div>
    <div id="pass" class="content"></div>
    <div id="bin" class="hidden content">
        <img id="binImg" src="bin.png">
    </div>
    <br id="scheduleBr">
    <div id="toolbox" class="hidden content">
        <img id=toolImg src="toolbox2.png">
    </div>
    <script src="${authScriptUrl}"></script>
</body>
</html>
