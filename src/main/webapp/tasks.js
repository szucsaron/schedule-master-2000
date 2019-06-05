let tasksTableEl;
let tasksTableBodyEl;

function appendTasks(tasks) {
    removeAllChildren(tasksTableBodyEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        appendTask(task);
    }
}

function appendTask(task) {
    /*const idTdEl = document.createElement('td');
    idTdEl.textContent = task.id;*/

    const btnEl = document.createElement("button");
    btnEl.textContent = task.title;
    btnEl.setAttribute("class", "scheduleBtn");
    const aEl = document.createElement('a');
    aEl.appendChild(btnEl);
    aEl.href = 'javascript:void(0);';
    aEl.dataset.taskId = task.id;
    aEl.addEventListener('click', onTaskClicked);

    const titleTdEl = document.createElement('td');
    titleTdEl.appendChild(aEl);
    titleTdEl.setAttribute("class", "btn");

    const contentTdEl = document.createElement('td');
    contentTdEl.textContent = task.content;
    contentTdEl.setAttribute("class", "btn");

    const delTdEl = createCheckBoxTd('tasks-del', task.id);
    delTdEl.setAttribute("class", "btn");

    /*const dateTdEl = document.createElement('td');
    dateTdEl.textContent = task.date.dayOfMonth + "." + task.date.monthValue + "." + task.date.year;*/

    const trEl = document.createElement('tr');
    // trEl.appendChild(idTdEl);
    trEl.appendChild(titleTdEl);
    trEl.appendChild(contentTdEl);
    trEl.appendChild(delTdEl)
    //trEl.appendChild(dateTdEl);
    tasksTableBodyEl.appendChild(trEl);
}

function onTasksLoad(tasks) {
    tasksTableEl = document.getElementById('tasks');
    tasksTableBodyEl = tasksTableEl.querySelector('tbody');

    appendTasks(tasks);
}

function onTaskClicked() {
    const taskId = this.dataset.taskId;
    hideBackToScheduleButton();
    showTask(taskId);
}

function showTask(taskId) {
    const params = new URLSearchParams();
    params.append('id', taskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'task?' + params.toString());
    xhr.send();
}


function onTaskLoad(schedules) {
    debugger;
    //taskId = schedules[0];
    //tasksTableBodyEl = tasksTableEl.querySelector('tbody');
    //const taskIdSpanEl = document.getElementById('task-id');
    const taskTitleSpanEl = document.getElementById('task-title');
    const taskContentSpanEl = document.getElementById('task-text');
    const taskSchedulesSpanEl = document.getElementById('task-schedules');
    const taskIdSpanEl = document.getElementById('hidden-id');

    removeAllChildren(taskSchedulesSpanEl);
    removeAllChildren(taskSchedulesSpanEl);

    taskIdSpanEl.value = schedules[0];
    taskIdSpanEl.dataset.taskId = schedules[0];
    taskTitleSpanEl.value = schedules[1];
    taskContentSpanEl.value = schedules[2];
    for(let i = 3; i < schedules.length-1; i+=2) {
        const scheduleLinkElement = document.createElement("a");
        scheduleLinkElement.textContent = schedules[i];
        scheduleLinkElement.dataset.scheduleId = schedules[i+1];
        scheduleLinkElement.addEventListener('click', onScheduleClicked);
        scheduleLinkElement.href = "javascript:void(0)";
        scheduleLinkElement.setAttribute("id", "scheduleLink");
        //taskSchedulesSpanEl.textContent += schedules[i] + " ";
        taskSchedulesSpanEl.appendChild(scheduleLinkElement);
        taskSchedulesSpanEl.appendChild(document.createElement("br"));
    }
}

function onTasksResponse() {
    if (this.status === OK) {
        showContents(['user-menu','tasks-content']);
        onTasksLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onTaskAddResponse() {
    clearMessages();
    if (this.status === OK) {
        appendTask(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onTaskAddClicked() {
    const taskFormEl = document.forms['task-form'];

    const titleInputEl = taskFormEl.querySelector('input[name="name"]');
    const contentInputEl = taskFormEl.querySelector('input[name="content"]');
    const title = titleInputEl.value;
    const content = contentInputEl.value;

    const params = new URLSearchParams();
    params.append('title', title);
    params.append('content', content);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/tasks');
    xhr.send(params);
}

function onTasksDeleteClicked() {
    const taskIdStrChain = getCheckBoxCheckedValues('tasks-del');
    const params = new URLSearchParams();
    params.append('taskIds', taskIdStrChain);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/tasks?' + params.toString())
    xhr.send();
}

function onTasksDeleteResponse() {

}