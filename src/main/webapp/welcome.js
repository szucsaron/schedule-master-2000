function onTasksClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/tasks');
    xhr.send();
}

function onSchedulesClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedules');
    xhr.send();
}

function onHomepageClicked(){
    console.log('home page');
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onPublicSchedulesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/public-schedules');
    xhr.send();
}

function onAdminMenuClicked(){
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAllUsersReceived);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/users');
    xhr.send();
}