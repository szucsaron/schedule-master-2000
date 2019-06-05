let usersTableEl;
let usersTableBodyEl;

let logsTableEl
let logsTableBodyEl;


function onAllUsersReceived() {
    if (this.status === OK) {
        showContents(['user-menu', 'users-content']);
        onAllUsersLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this); // needs update 
    }
}

function onAllUsersLoad(users) {
    usersTableEl = document.getElementById('users-table');
    usersTableBodyEl = usersTableEl.querySelector('tbody');

    appendUsersTable(users);
}

function appendUsersTable(users) { // 2
    removeAllChildren(usersTableBodyEl);

    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        appendUsers(user);
    }
}

function appendUsers(user) { // 3
    const aEl = document.createElement('a');
    aEl.textContent = user.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.userId = user.id;
    aEl.addEventListener('click', onUserClicked);

    const idTdEl = document.createElement('td');
    idTdEl.textContent = user.id;

    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);

    const emailTdEl = document.createElement('td');
    emailTdEl.textContent = user.email;

    const roleTdEl = document.createElement('td');
    roleTdEl.textContent = user.role;

    const trEl = document.createElement('tr');

    trEl.appendChild(idTdEl);
    trEl.appendChild(nameTdEl);
    trEl.appendChild(emailTdEl);
    trEl.appendChild(roleTdEl);
    usersTableBodyEl.appendChild(trEl);
}

function onUserClicked() {
    const userId = this.dataset.userId;

    const params = new URLSearchParams();
    params.append('userId', userId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUserSchedulesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedules?' + params.toString());
    xhr.send();
}

function onLoggerClicked() {

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAllLogsReceived);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/logger');
    xhr.send();
}

function onAllLogsReceived() {
    if (this.status === OK) {
        showContents(['user-menu','logs-content']);
        onAllLogsLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this); // needs update 
    }
}

function onAllLogsLoad(logs) {
    logsTableEl = document.getElementById('logs-table');
    logsTableBodyEl = logsTableEl.querySelector('tbody');

    appendLogsTable(logs);
}

function appendLogsTable(logs) { 
    removeAllChildren(logsTableBodyEl);

    for (let i = 0; i < logs.length; i++) {
        const log = logs[i];
        appendLogs(log);
    }
}

function appendLogs(log) { // 3

    const dateTdEl = document.createElement('td');
    const date = new Date(log.date);
    const options = {hour12:false, year: 'numeric', month: 'long', day: 'numeric' , hour: 'numeric', minute: 'numeric', second: 'numeric'};
    dateTdEl.textContent = date.toLocaleDateString("en-US", options)

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = log.username;

    const typeTdEl = document.createElement('td');
    typeTdEl.textContent = log.type;

    const locationTdEl = document.createElement('td');
    locationTdEl.textContent = log.location;

    const messageTdEl = document.createElement('td');
    messageTdEl.textContent = log.msg;

    const trEl = document.createElement('tr');

    trEl.appendChild(dateTdEl);
    trEl.appendChild(nameTdEl);
    trEl.appendChild(typeTdEl);
    trEl.appendChild(locationTdEl);
    trEl.appendChild(messageTdEl);
    logsTableBodyEl.appendChild(trEl);
}