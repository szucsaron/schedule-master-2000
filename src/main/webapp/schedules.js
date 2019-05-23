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

function appendPublicSchedules(schedules) { // 2
    removeAllChildren(schedulesTableBodyEl);

    for (let i = 0; i < schedules.length; i++) {
        const schedule = schedules[i];
        appendPublicSchedule(schedule);
    }
}

function onSchedulesLoad(schedules) {
    schedulesTableEl = document.getElementById('schedules');
    schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');

    appendSchedules(schedules);
}

function onPublicSchedulesLoad(schedules) { // 1
    schedulesTableEl = document.getElementById('pubschedules');
    schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');

    appendPublicSchedules(schedules);
}

function onScheduleClicked() {
    const scheduleId = this.dataset.scheduleId;

    const params = new URLSearchParams();
    params.append('id', scheduleId);
    params.append('isPublic','false');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'schedule?' + params.toString());
    xhr.send();
}

function onPublicScheduleClicked() { // 4
    const scheduleId = this.dataset.scheduleId;
    
    const params = new URLSearchParams();
    params.append('id', scheduleId);
    params.append('isPublic','true');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onPublicScheduleResponse);
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


function onPublicScheduleResponse() { //5
    if (this.status === OK) {
        clearMessages();
        showContents(['user-menu', 'schedule-content']);
        showPublicSchedule(JSON.parse(this.responseText));
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

function onPublicSchedulesResponse() {
    if (this.status === OK) {
        showContents(['user-menu','public-schedules']);
        onPublicSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function appendSchedule(schedule) {
    const aEl = document.createElement('a');
    aEl.textContent = schedule.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.scheduleId = schedule.id;
    aEl.setAttribute("id", "schedulename" + schedule.id)
    aEl.addEventListener('click', onScheduleClicked);


    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);
    nameTdEl.setAttribute("contenteditable", true);
    nameTdEl.setAttribute("id", "schedulenameplus" + schedule.id)
    const contentTdEl = document.createElement('td');
    contentTdEl.textContent = schedule.content;
    const startingTdEl = document.createElement('td');
    let startingDate = new Date(convertDate(schedule.startingDate));
    startingTdEl.textContent = getDateStr(startingDate);
    startingTdEl.setAttribute("contenteditable", true);
    startingTdEl.setAttribute("id", "schedulestart" + schedule.id)
    console.log(schedule);
    const scheduleFinish = startingDate.addDays(schedule.durationInDays);

    const finishingTdEl = document.createElement('td');
    finishingTdEl.textContent = getDateStr(scheduleFinish);
    finishingTdEl.setAttribute("contenteditable", true);
    finishingTdEl.setAttribute("id", "schedulefinish" + schedule.id)
    const trEl = document.createElement('tr');

    const delChkBox = createCheckBoxTd('schedules-del', schedule.id);

    trEl.appendChild(nameTdEl);
    trEl.appendChild(startingTdEl);
    trEl.appendChild(finishingTdEl);
    trEl.appendChild(delChkBox);
    schedulesTableBodyEl.appendChild(trEl);
}

function appendPublicSchedule(schedule) { // 3
    const aEl = document.createElement('a');
    aEl.textContent = schedule.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.scheduleId = schedule.id;
    aEl.addEventListener('click', onPublicScheduleClicked);

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

function onSchedulesDeleteClicked() {
    const idStrChain = getCheckBoxCheckedValues('schedules-del');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/schedules?scheduleIds=' + idStrChain);
    xhr.send();
}

function onSchedulesUpdateClicked() {
    debugger;
    const idStrChain = getCheckBoxCheckedValues('schedules-del');
    console.log(idStrChain);
    var ids  = idStrChain.split(",");
    var newNames = new Array();
    var newStarts = new Array();
    var newFinishes = new Array();

    var i;
    for (i = 0; i < ids.length; i++) {
    newNames.push(document.getElementById("schedulenameplus" + ids[i]).textContent);
    newStarts.push(document.getElementById("schedulestart" + ids[i]).innerHTML);
    newFinishes.push(document.getElementById("schedulefinish" + ids[i]).innerHTML);
    }

    const params = new URLSearchParams();
    params.append("ids", idStrChain);
    params.append('newNames', newNames);
    params.append("newStarts", newStarts);
    params.append("newFinishes", newFinishes);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/schedules?' + params.toString());
    xhr.send();
}