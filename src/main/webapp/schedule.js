let gSchedulePrivate = true;
let gScheduleTable = null;
let gSelectResult = null;

let gDragResult = null;
let gDropResult = null;
let gClickResult = null;

let gDragMode;
let scheduleId;


function showSchedule(scheduleTaskDto) {
    const scheduleContentEl = document.getElementById("schedule-content");
    removeAllChildren(scheduleContentEl);

    gScheduleId = scheduleTaskDto.schedule.id;
    gScheduleTable = new ScheduleTable("scheduleTable", scheduleTaskDto.schedule, scheduleTaskDto.taskDto)
    if (gSchedulePrivate) {
        gScheduleTable.onFieldDragged = onTableFieldDragged;
        gScheduleTable.onFieldDropped = onTableFieldDropped;
        gScheduleTable.onFieldClicked = onTableFieldClicked;

    }
    const tableContent = gScheduleTable.generateDom();
    scheduleContentEl.appendChild(tableContent);

    document.getElementById('bin').addEventListener('drop', onBinMouseUp);
    document.getElementById('toolbox').addEventListener('drop', onEditMouseUp);
    displayTaskPopup()
}

function refreshSchedule() {
    onScheduleTaskModified();
}


function showPublicSchedule(scheduleTaskDto) { //6
    const scheduleContentEl = document.getElementById("schedule-content");
    removeAllChildren(scheduleContentEl);
    
    gScheduleId = scheduleTaskDto.schedule.id;
    gScheduleTable = new ScheduleTable("scheduleTable", scheduleTaskDto.schedule, scheduleTaskDto.taskDto)
    /*if (gSchedulePrivate) {
        gScheduleTable.onFieldDragged = onTableFieldDragged;
        gScheduleTable.onFieldDropped = onTableFieldDropped;
    }*/
    const tableContent = gScheduleTable.generateDom();
    scheduleContentEl.appendChild(tableContent);

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
    var toDisplay = document.getElementById('pass');
    removeAllChildren(toDisplay);
    toDisplay.appendChild(document.createElement('br'));
    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        const taskButton = document.createElement('text');
        taskButton.innerHTML = task.title;
        taskButton.className = 'taskButton';
        taskButton.setAttribute('taskId', task.id);
        taskButton.setAttribute('draggable', true);

        taskButton.addEventListener('dragstart', onTaskListElementDragged);
        taskButton.addEventListener('click', onTaskListElementClicked);
        toDisplay.appendChild(taskButton);
        toDisplay.appendChild(document.createElement('br'));
    }
}

function onTaskListElementClicked() {
    gClickResult = {}
    gClickResult.task = {};
    gClickResult.task.task = {};
    gClickResult.task.task.id = this.getAttribute('taskid');
    gClickResult.element = this;
}

function onTaskListElementDragged() {
    gDragResult = {}
    gDragResult.task = {};
    gDragResult.task.task = {};
    gDragResult.task.task.id = this.getAttribute('taskid');
}

// Table user operations

function isTaskSelected() {
    return gSelectedTaskId != undefined;
}

function onTableFieldClicked(res) {
    gClickResult = res;
}

function onTableFieldDragged(res) {
    gDragResult = res;
    if (gClickResult) {
        res.element.textContent = gClickResult.element.textContent;
    }

}

function onTableFieldDropped(res) {
    gDropResult = res;
    if (gDropResult.task == null) {
        if (gClickResult) {
            insertTaskInTable();
        } else if (gDragResult.task != null) {
            if (gDragResult.clickedDay == gDropResult.clickedDay) {
                changeExistingTasTimekInTable()
            } else {
                dragTaskToDifferentDayInTable();
            }
        }
    }
    gDragResult = null;
    gDropResult = null;
    gClickResult = null;
}

// Implementing triggerd modification operations

function insertTaskInTable() {
    modifyScheduleTask(gClickResult.task.task.id,
                       gDragResult.clickedDay,
                       gDragResult.clickedHour,
                       gDropResult.clickedHour);
}

function changeExistingTasTimekInTable() {
    modifyScheduleTask(gDragResult.task.task.id,
                       gDragResult.clickedDay,
                       gDragResult.clickedHour,
                       gDropResult.clickedHour);
}

function dragTaskToDifferentDayInTable() {
    modifyScheduleTask(gDragResult.task.task.id,
                       gDropResult.clickedDay,
                       gDropResult.clickedHour,
                       gDropResult.clickedHour);
}

function modifyScheduleTask(taskId, day, hourStart, hourEnd) {
    console.log("id: " + taskId + "day: " + day + "hstart: " + hourStart + "hend: " + hourEnd)
    const params = new URLSearchParams();    
    params.append('scheduleId', gScheduleTable.schedule.id)
    params.append('taskId', taskId)
    params.append('day', day)
    params.append('hourStart', hourStart)
    params.append('hourEnd', hourEnd)

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleTaskModified);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'scheduleTable?');
    xhr.send(params);
}

function onScheduleTaskModified() {
    gSelectedTaskId = undefined;
    gSelectedTaskName = undefined;
    const scheduleId = gScheduleTable.schedule.id;
    
    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleRefresh);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'schedule?' + params.toString());
    xhr.send();

}


function changeTaskInTable(res, params) {
    if (gDragStartDay == res.clickedDay) {
        params.append('hourStart', gDragStartHour);
        params.append('hourEnd', res.clickedHour);
    } else {
        params.append('hourStart', res.clickedHour);
        params.append('hourEnd', res.clickedHour);
    }
}

function placeTaskInTable(res, params) {
    params.append('hourStart', res.clickedHour);
    params.append('hourEnd', res.clickedHour);
    console.log(params.toString());
}

function moveTaskInTable(res, params) {
    params.append('hourStart', gDragStartHour);
    params.append('hourEnd', res.clickedHour);
}

// *********************

function initDetachTask(res) {
    gSelectedTaskId = res.task.task.id;
}

function onScheduleRefresh() {
    showSchedule(JSON.parse(this.responseText));
}

function onBinMouseUp() {
    const params = new URLSearchParams();
    params.append('scheduleId', gScheduleTable.schedule.id);
    params.append('taskId', gDragResult.task.task.id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleTaskModified);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'scheduleTable?' + params.toString());
    xhr.send();
}

function onEditMouseUp() {
    showTask(gDragResult.task.task.id);
    showBackToScheduleButton();
}

function setScheduleAsPublic() {
    gSchedulePrivate = false;
}

function setScheduleAsPrivate() {
    gSchedulePrivate = true;
}