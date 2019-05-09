let gScheduleTable;
let gSelectedTaskId;
let scheduleId;


function showSchedule(scheduleTaskDto) {
    const scheduleContentEl = document.getElementById("schedule-content");
    removeAllChildren(scheduleContentEl);
    gScheduleId = scheduleTaskDto.schedule.id;
    gScheduleTable = new ScheduleTable("scheduleTable", scheduleTaskDto.schedule, scheduleTaskDto.taskDto)
    gScheduleTable.onFieldDragged = onTableFieldDragged;
    gScheduleTable.onFieldDropped = onTableFieldDropped;

    const tableContent = gScheduleTable.generateDom();
    scheduleContentEl.appendChild(tableContent);
    displayTaskPopup()
}

function showTasks(){

}

function deleteSchedule(){

}

function assignTask(){

}

function removeTask(){

}



//experimental code



function addTaskToDate() {
    const params = new URLSearchParams();
    params.append('scheduleId', gScheduleId);
    params.append("taskId", this.getAttribute("taskId"));
    params.append("row", gRow);
    params.append("col", gCol);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'addTask?' + params.toString());
    xhr.send();
}
// old
function onTableBoxClicked(res) {
    const taskDto = res.task;
    const params = new URLSearchParams();
    params.append('scheduleId', gScheduleTable.schedule.id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', displayTaskPopup);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'addTask?' + params.toString());
    xhr.send();
}

function displayTaskPopup() {
    const tasks = gScheduleTable.tasks;
    var toDisplay = document.getElementById("pass");
    removeAllChildren(toDisplay);
    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i].task;
        const taskButton = document.createElement("button");
        taskButton.textContent = task.title;
        taskButton.setAttribute("taskId", task.id);
        taskButton.addEventListener('click', onTaskSelectClicked);
        toDisplay.appendChild(taskButton);
    }
    //openWin();
}

function onTaskSelectClicked() {
    gSelectedTaskId = this.getAttribute("taskId");
}

function onTableFieldDragged(res) {
    console.log(res);
}

function onTableFieldDropped(res) {
    alert("fasz")
    console.log(res);
}