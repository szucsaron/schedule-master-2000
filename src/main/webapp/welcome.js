function onProfileLoad(user) {
    clearMessages();
    showContents(['profile-content', 'logout-content']);

    const userEmailSpandEl = document.getElementById('user-email');
    const userPasswordSpanEl = document.getElementById('user-password');

    userEmailSpandEl.textContent = user.email;
    userPasswordSpanEl.textContent = user.password;

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/tasks');
    xhr.send();
}
