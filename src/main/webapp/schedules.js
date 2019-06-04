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
        const responseTextContent = JSON.parse(this.responseText);
        showContents(['user-menu','schedules-content']);
        onSchedulesLoad(responseTextContent.schedules);
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function onPublicSchedulesResponse() {
    if (this.status === OK) {
        const h1 = document.getElementById('public-schedules-h1');
        h1.textContent= 'Public Schedules';
        showContents(['user-menu','public-schedules']);
        onPublicSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this);
    }
}


function onUserSchedulesResponse() {
    if (this.status === OK) {
        const response = JSON.parse(this.responseText);
        const h1 = document.getElementById('public-schedules-h1');
        h1.textContent= response.user.name + '\'s Schedules';
        showContents(['user-menu','public-schedules']);
        onPublicSchedulesLoad(response.schedules);
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
    const scheduleFinish = startingDate.addDays(schedule.durationInDays);

    const finishingTdEl = document.createElement('td');
    finishingTdEl.textContent = getDateStr(scheduleFinish);
    finishingTdEl.setAttribute("contenteditable", true);
    finishingTdEl.setAttribute("id", "schedulefinish" + schedule.id);

    const shareEl = generateShareDt(schedule);
    shareEl.setAttribute("class", "btn");


    const delChkBox = createCheckBoxTd('schedules-del', schedule.id);
    delChkBox.setAttribute("class", "btn");
    
    const trEl = document.createElement('tr');
    trEl.appendChild(nameTdEl);
    trEl.appendChild(startingTdEl);
    trEl.appendChild(finishingTdEl);
    trEl.appendChild(shareEl);
    trEl.appendChild(delChkBox);
    schedulesTableBodyEl.appendChild(trEl);
}

function generateShareDt(schedule) {
    const shareEl = document.createElement('td');
    const shareBtEl = document.createElement('button');
    shareBtEl.dataset.scheduleId = schedule.id;
    shareEl.appendChild(shareBtEl);
    if (schedule.public) {
        shareBtEl.textContent = 'Unshare';
        shareBtEl.dataset.isPublic = 'true';
        const getLinkBtEl = document.createElement('button');
        getLinkBtEl.textContent = 'Get Link';
        getLinkBtEl.dataset.scheduleId = schedule.id;
        getLinkBtEl.addEventListener('click', onGetSharedScheduleLinkClicked);
        shareEl.appendChild(getLinkBtEl);
    } else {
        shareBtEl.textContent = 'Share';
        shareBtEl.dataset.isPublic = 'false';
    }
    shareBtEl.addEventListener('click', onScheduleShareClicked);
    return shareEl;
}

function appendPublicSchedule(schedule) { // 3
    const aEl = document.createElement('a');
    aEl.textContent = schedule.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.scheduleId = schedule.id;
//    aEl.setAttribute('href', 'share?schedule_id=' + schedule.id)
    aEl.addEventListener('click', onPublicScheduleClicked);


    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);
    const contentTdEl = document.createElement('td');
    contentTdEl.textContent = schedule.content;
    const startingTdEl = document.createElement('td');
    let startingDate = new Date(convertDate(schedule.startingDate));
    startingTdEl.textContent = getDateStr(startingDate);
    const scheduleFinish = startingDate.addDays(schedule.durationInDays);

    const userNameTdEl = document.createElement('td');
    userNameTdEl.textContent = schedule.creatorsName;

    const finishingTdEl = document.createElement('td');
    finishingTdEl.textContent = getDateStr(scheduleFinish);
    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    trEl.appendChild(startingTdEl);
    trEl.appendChild(finishingTdEl);
    trEl.appendChild(userNameTdEl);
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

function onScheduleShareClicked() {
    console.log(this.dataset.isPublic);
    const params = new URLSearchParams();
    let isPublic;
    switch (this.dataset.isPublic) {
        case 'true':
            isPublic = false;
            break;
        case 'false':
            isPublic = true;
            break;
    }
    params.append('schedule_id', this.dataset.scheduleId);
    params.append('is_public', isPublic);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/shareUpdate');
    xhr.send(params);
}

function onPublicScheduleLinkCloseClicked() {
    document.getElementById('public-schedule-link').classList.add('hidden');
}

function onGetSharedScheduleLinkClicked() {
    const link =  getBaseUrl() + '/share?schedule_id=' + this.dataset.scheduleId;
    const el = document.getElementById('public-schedule-link');
    el.classList.remove('hidden');
    document.getElementById('public-schedule-link-txt').textContent = link;
}