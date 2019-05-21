let schedulesTableEl;
let schedulesTableBodyEl;
let gScheduleId;

function appendSchedules(schedules) {
    removeAllChildren(schedulesTableBodyEl);

    for (let i = 0; i < schedules.length; i++) {
        const schedule = schedules[i];
        appendSchedule(schedule);
    }
}

function onSchedulesLoad(schedules) {
    schedulesTableEl = document.getElementById('schedules');
    schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');

    appendSchedules(schedules);
}

function onScheduleClicked() {
    const scheduleId = this.dataset.scheduleId;

    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'schedule?' + params.toString());
    xhr.send();
}

function onScheduleResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['bin', 'toolbox', 'pass', 'schedule-content', 'back-to-profile-content', 'logout-content']);
        showSchedule(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function onSchedulesResponse() {
    if (this.status === OK) {
        showContents(['schedules-content', 'back-to-profile-content', 'logout-content']);
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

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

    let newDate = new Date(convertDate(schedule.startingDate));
    console.log(schedule);
    const scheduleFinish = newDate.addDays(schedule.durationInDays);
    // alert(scheduleFinish.toString())

    const finishingTdEl = document.createElement('td');
    finishingTdEl.textContent = scheduleFinish.getDate() + "." + (scheduleFinish.getMonth() + 1) + "." + scheduleFinish.getFullYear();
    const trEl = document.createElement('tr');
    // trEl.appendChild(idTdEl);
    trEl.appendChild(nameTdEl);
    trEl.appendChild(startingTdEl);
    trEl.appendChild(finishingTdEl);
    schedulesTableBodyEl.appendChild(trEl);
}



function onScheduleAddResponse() {
    clearMessages();
    if (this.status === OK) {
        appendSchedule(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function onScheduleAddClicked() {
    const scheduleFormEl = document.forms['schedule-form'];

    const nameInputEl = scheduleFormEl.querySelector('input[name="name"]');
    const dateInputEl = scheduleFormEl.querySelector('input[name="starting-date"]');
    const durationInputEl = scheduleFormEl.querySelector('input[name="schedule-duration"]');

    const name = nameInputEl.value;
    const date = dateInputEl.value;
    const duration = durationInputEl.value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('date', date);
    params.append('duration', duration);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/schedules');
    xhr.send(params);
}