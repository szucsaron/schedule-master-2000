let gSchedulePrivate = false;
let gScheduleTable;
let gSelectedTaskId;
let gDragStartHour
let gDragStartDay
let scheduleId;


function showSchedule(scheduleTaskDto) {
    const scheduleContentEl = document.getElementById("schedule-content");
    removeAllChildren(scheduleContentEl);

    gScheduleId = scheduleTaskDto.schedule.id;
    gScheduleTable = new ScheduleTable("scheduleTable", scheduleTaskDto.schedule, scheduleTaskDto.taskDto)
    if (gSchedulePrivate) {
        gScheduleTable.onFieldDragged = onTableFieldDragged;
        gScheduleTable.onFieldDropped = onTableFieldDropped;
    }
    const tableContent = gScheduleTable.generateDom();
    scheduleContentEl.appendChild(tableContent);

    document.getElementById('bin').addEventListener('mouseup', onBinMouseUp);
    document.getElementById('toolbox').addEventListener('mouseup', onEditMouseUp);
    displayTaskPopup()
}

function displayTaskPopup() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUserTasksReceived);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/tasks');
    xhr.send();
}

function onUserTasksReceived() {
    const tasks = JSON.parse(this.responseText);
    var toDisplay = document.getElementById("pass");
    removeAllChildren(toDisplay);
    toDisplay.appendChild(document.createElement('br'));
    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        const taskButton = document.createElement("button");
        taskButton.innerHTML = task.title;
        taskButton.className = "taskButton";
        taskButton.setAttribute("taskId", task.id);
        taskButton.addEventListener('click', onTaskSelectClicked);
        toDisplay.appendChild(taskButton);
        toDisplay.appendChild(document.createElement('br'));
    }
}

function onTaskSelectClicked() {
    gSelectedTaskId = this.getAttribute("taskId");
}

function onTableFieldDragged(res) {
    if (res.task == null) {
        initModifyTask(res);
    } else {
        initDetachTask(res);
    }
}

function initModifyTask(res) {
    gDragStartDay = res.clickedDay;
    gDragStartHour = res.clickedHour;
}

function initDetachTask(res) {
    gSelectedTaskId = res.task.task.id;
}

function onTableFieldDropped(res) {
    if (res.task == null) {
        const params = new URLSearchParams();
        params.append('scheduleId', gScheduleTable.schedule.id);
        params.append('taskId', gSelectedTaskId);
        params.append('day', gDragStartDay);
        params.append('hourStart', gDragStartHour);
        params.append('hourEnd', res.clickedHour);
    
        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onTasksModified);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'scheduleTable?');
        xhr.send(params);
    }
}

function onTasksModified() {
    const scheduleId = gScheduleTable.schedule.id;
    
    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleRefresh);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'schedule?' + params.toString());
    xhr.send();
}

function onScheduleRefresh() {
    showSchedule(JSON.parse(this.responseText));
}

function onBinMouseUp() {
    const params = new URLSearchParams();
    params.append('scheduleId', gScheduleTable.schedule.id);
    params.append('taskId', gSelectedTaskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksModified);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'scheduleTable?' + params.toString());
    xhr.send();
}

function onEditMouseUp() {
    showTask(gSelectedTaskId);
    showBackToScheduleButton();
}

function setScheduleAsPublic() {
    gSchedulePrivate = false;
}

function setScheduleAsPrivate() {
    gSchedulePrivate = true;
}