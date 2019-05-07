let taskId;


function appendTask(task) {
    const idTdEl = document.createElement('td');
    idTdEl.textContent = task.id;

    const aEl = document.createElement('a');
    aEl.textContent = task.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.taskId = task.id;
    aEl.addEventListener('click', onTaskClicked);

    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);

    const contentTdEl = document.createElement('td');
    contentTdEl.textContent = task.content;

    const trEl = document.createElement('tr');
    trEl.appendChild(idTdEl);
    trEl.appendChild(nameTdEl);
    trEl.appendChild(contentTdEl);
    tasksTableBodyEl.appendChild(trEl);
}

function onTaskResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['tasks-content', 'back-to-profile-content', 'logout-content']);
        onTaskLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}

