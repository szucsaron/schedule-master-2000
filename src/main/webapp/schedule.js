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

        taskButton.addEventListener('drag', onTableFieldSelected);
        toDisplay.appendChild(taskButton);
        toDisplay.appendChild(document.createElement('br'));
    }
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
}

function insertTaskInTable() {
    alert('insert')
}

function changeExistingTasTimekInTable() {
    alert('change time')
}

function dragTaskToDifferentDayInTable() {
    alert('change day')
}

/*
 if (res.task == null) {
        const params = new URLSearchParams();
        params.append('scheduleId', gScheduleTable.schedule.id);
        params.append('taskId', gSelectedTaskId);
        params.append('day', res.clickedDay);

        if (gDragMode == "change") {
            changeTaskInTable(res, params)
        } else if (gDragMode == 'new') {
            placeTaskInTable(res, params);
        } else if (gDragMode == 'move') {
            moveTaskInTable(res, params);
        }
        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onTasksModified);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'scheduleTable?');
        xhr.send(params);
    }
    gDragMode = undefined;
*/

// *************************************************

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

function onTasksModified() {
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