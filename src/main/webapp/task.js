let taskId;


function appendTask(task) {
    /*const idTdEl = document.createElement('td');
    idTdEl.textContent = task.id;*/

    const aEl = document.createElement('a');
    aEl.textContent = task.title;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.taskId = task.id;
    aEl.addEventListener('click', onTaskClicked);

    const titleTdEl = document.createElement('td');
    titleTdEl.appendChild(aEl);

    const contentTdEl = document.createElement('td');
    contentTdEl.textContent = task.content;

    /*const dateTdEl = document.createElement('td');
    dateTdEl.textContent = task.date.dayOfMonth + "." + task.date.monthValue + "." + task.date.year;*/

    const trEl = document.createElement('tr');
    // trEl.appendChild(idTdEl);
    trEl.appendChild(titleTdEl);
    trEl.appendChild(contentTdEl);
    //trEl.appendChild(dateTdEl);
    tasksTableBodyEl.appendChild(trEl);
}

function onTaskResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['task-content', 'back-to-profile-content', 'logout-content']);
        onTaskLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

function onTaskBackToScheduleClicked() {
    showContents(['bin', 'toolbox', 'pass', 'schedule-content', 'back-to-profile-content', 'logout-content']);
}

function hideBackToScheduleButton() {
    document.getElementById('task-back-to-schedule').classList.add('hidden');
}

function showBackToScheduleButton() {
    document.getElementById('task-back-to-schedule').classList.remove('hidden');
}

function onTaskUpdate() {
    debugger;
    const id = document.getElementById("hidden-id");
    const title = document.getElementById("task-title");
    const text = document.getElementById("task-text");

    const params = new URLSearchParams();
    params.append('id', id);
    params.append('title', title);
    params.append('text', text);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'task');
    xhr.send(params);
}