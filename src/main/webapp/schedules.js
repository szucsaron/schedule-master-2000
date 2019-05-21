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
        showContents(['user-menu','bin', 'toolbox', 'pass', 'schedule-content']);
        showSchedule(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function onSchedulesResponse() {
    if (this.status === OK) {
        showContents(['user-menu','schedules-content']);
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function appendSchedule(schedule) {
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
    let startingDate = new Date(convertDate(schedule.startingDate));
    startingTdEl.textContent = getDateStr(startingDate);
    console.log(schedule);
    const scheduleFinish = startingDate.addDays(schedule.durationInDays);

    const finishingTdEl = document.createElement('td');
    finishingTdEl.textContent = getDateStr(scheduleFinish);
    const trEl = document.createElement('tr');

    const delChkBox = createCheckBoxTd('schedules-del', schedule.id);

    trEl.appendChild(nameTdEl);
    trEl.appendChild(startingTdEl);
    trEl.appendChild(finishingTdEl);
    trEl.appendChild(delChkBox);
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

function onSchedulesDeleteClicked() {
    const idStrChain = getCheckBoxCheckedValues('schedules-del');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/schedules?scheduleIds=' + idStrChain);
    xhr.send();
}