function onProfileLoad(user) {
    clearMessages();
    showContents(['user-menu','profile-content']);

    const userEmailSpandEl = document.getElementById('user-email');
    const userPasswordSpanEl = document.getElementById('user-password');

    userEmailSpandEl.textContent = user.email;
    userPasswordSpanEl.textContent = user.password;
}

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
    xhr.open('GET', 'schedules');
    xhr.send();
}

function onBackToProfileClicked() {
    showContents(['user-menu','profile-content']);
}
