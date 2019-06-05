function onLoad() {
    loginContentDivEl = document.getElementById('login-content');
    registerContentDivEl = document.getElementById('register-content');
    profileContentDivEl = document.getElementById('profile-content');
    taskContentDivEl = document.getElementById('task-content');
    tasksContentDivEl = document.getElementById('tasks-content');
    scheduleContentDivEl = document.getElementById('schedule-content');
    schedulesContentDivEl = document.getElementById('schedules-content');
    backToProfileContentDivEl = document.getElementById('back-to-profile-content');
    logoutContentDivEl = document.getElementById('logout-content');
    taskListContentDivEl = document.getElementById('task-list');
    showLinkContentDivEl = document.getElementById('public-schedule-link');

    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

    const registerButtonEl = document.getElementById('register-button');
    registerButtonEl.addEventListener('click', onRegisterButtonClicked);

    const backToLoginButtonEl = document.getElementById('back-to-login-button');
    backToLoginButtonEl.addEventListener('click', onBackToLoginButtonClicked);

    const registrationButtonEl = document.getElementById('registration-button');
    registrationButtonEl.addEventListener('click', onRegistrationButtonClicked);

    const logoutButtonEl = document.getElementById('logout-button');
    logoutButtonEl.addEventListener('click', onLogoutButtonClicked);
}