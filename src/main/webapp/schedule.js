

function showTasks(){

}

function deleteSchedule(){

}

function assignTask(){

}

function removeTask(){

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
    startingTdEl.textContent = schedule.startingDate.dayOfMonth + "." + schedule.startingDate.monthValue + "." + schedule.startingDate.year;

    const scheduleFinish = schedule.startingDate.addDays(schedule.durationInDays);

    const finishingTdEl = document.createElement('td');
    finishingTdEl.textContent = scheduleFinish.dayOfMonth + "." + scheduleFinish.monthValue + "." + scheduleFinish.year;

    const trEl = document.createElement('tr');
    // trEl.appendChild(idTdEl);
    trEl.appendChild(nameTdEl);
    trEl.appendChild(startingTdEl);
    trEl.appendChild(finishingTdEl);
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