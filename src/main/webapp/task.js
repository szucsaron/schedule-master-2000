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

