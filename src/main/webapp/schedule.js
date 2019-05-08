function onHourClicked(){
    // Displays taskId. Now for debug only. 
    const coords = getFieldCoords(this);
    const col = coords[0];
    const row = coords[1];
    alert("taskId " + this.getAttribute("taskId"));
}

function showTasks(){

}

function deleteSchedule(){

}

function assignTask(){

}

function removeTask(){

}

function showTable(schedule){
    let tEl = document.getElementById("scheduleTable");
    if (tEl != null) {
        tEl.remove();
    }
    header = ["Date 1", "Date 2", "Date 3", "Date 4", "Date 5", "Date 6", "Date 7"];
    tEl = createTable("scheduleTable", 24, header, onHourClicked);
    document.getElementById("schedule").appendChild(tEl);

    clearTable(tEl);
    printScheduleToTable(tEl, schedule);
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
    tdEl.textContent = task.name;
    tdEl.setAttribute("taskId", task.id);
}

//experimental code

let scheduleId;


function appendSchedule(schedule) {
    /*const idTdEl = document.createElement('td');
    idTdEl.textContent = schedule.id;*/

    const aEl = document.createElement('a');
    aEl.textContent = schedule.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.scheduleId = schedule.id;
    aEl.addEventListener('click', onScheduleClicked);

    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);

    const contentTdEl = document.createElement('td');
    contentTdEl.textContent = schedule.content;

    const startingTdEl = document.createElement('td');
    startingTdEl.textContent = schedule.startingDate.dayOfMonth + "." + schedule.startingDate.monthValue + "." + scheduleDate.starting.year;

    const finishingTdEl = document.createElement('td');
    finishingTdEl.textContent = schedule.finishing.dayOfMonth + "." + schedule.finishing.monthValue + "." + schedule.finishing.year;

    const trEl = document.createElement('tr');
    // trEl.appendChild(idTdEl);
    trEl.appendChild(nameTdEl);
    /*trEl.appendChild(startingTdEl);
    trEl.appendChild(finishingTdEl);*/
    schedulesTableBodyEl.appendChild(trEl);
}

function onScheduleResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['schedules-content', 'back-to-profile-content', 'logout-content']);
        onScheduleLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}