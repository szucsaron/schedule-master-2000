let tasksTableEl;
let tasksTableBodyEl;

function appendTasks(tasks) {
    removeAllChildren(tasksTableBodyEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        appendTask(task);
    }
}

function onTasksLoad(tasks) {
    tasksTableEl = document.getElementById('tasks');
    tasksTableBodyEl = tasksTableEl.querySelector('tbody');

    appendTasks(tasks);
}

function onTaskClicked() {
    const taskId = this.dataset.taskId;

    const params = new URLSearchParams();
    params.append('id', taskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/task?' + params.toString());
    xhr.send();
}



function onTaskLoad(taskDto) {
    taskId = taskDto.task.id;

    const taskIdSpanEl = document.getElementById('task-id');
    const taskNameSpanEl = document.getElementById('task-name');
    const taskContentSpanEl = document.getElementById('task-content');

    taskIdSpanEl.textContent = taskDto.task.id;
    taskNameSpanEl.textContent = taskDto.task.name;
    taskContentSpanEl.textContent = taskDto.task.content;
}

function onTasksResponse() {
    if (this.status === OK) {
        showContents(['tasks-content', 'back-to-profile-content', 'logout-content']);
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

    const nameInputEl = taskFormEl.querySelector('input[name="name"]');
    const contentInputEl = taskFormEl.querySelector('input[name="content"]');

    const name = nameInputEl.value;
    const content = contentInputEl.value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('content', content);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskAddResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/tasks');
    xhr.send(params);
}


