function onHourClicked(){

}

function showTasks(){

}

function deleteSchedule(){

}

function assignTask(){

}

function removeTask(){

}

function showTable(){

}

function printScheduleToTable(tableEl, schedule) {
    const tasks = schedule.tasks;
    const id = tableEl.getAttribute("id");
    for (let i = 0; i < tasks.length; i++) {
        const day = tasks[i].day;
        const startHour = tasks[i].startHour;
        const endHour = tasks[i].endHour;

        const tdEl = getTableField(id, day, startHour - 1);

        assignTaskToField(tdEl, tasks[i])
        for (let h = startHour; h < endHour - 1; h++) {
            console.log(h)
            const tdEl = getTableField(id, day, h);
            assignTaskToField(tdEl, tasks[i])
        }
    }
}

function assignTaskToField(fEl, task) {
    tdEl.textContent = task.title;
    tdEl.setAttribute("taskId", task.id);
}