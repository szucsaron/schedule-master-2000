let taskId;


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
    const id = document.getElementById("hidden-id").getAttribute("value");
    const title = document.getElementById("task-title").value;
    const content = document.getElementById("task-text").value;
    const params = new URLSearchParams();
    params.append('id', id);
    params.append('title', title);
    params.append('content', content);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', showTask.bind(null, id));
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'task?' + params.toString());
    xhr.send();
}