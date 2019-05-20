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