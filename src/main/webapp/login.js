let adminMenuTdEl;
let logMenuTdEl;

function onLoginResponse() {
    
    if (this.status === OK) {
        
        const user = JSON.parse(this.responseText);
        console.log('arses');
        console.log(user);
        setAuthorization(user);
        if(user.role == 'ADMIN'){

            adminMenuTdEl = document.createElement('td');
            let adminMenuEl = document.createElement('a');
            
            logMenuTdEl = document.createElement('td');
            let logMenuEl = document.createElement('a');

            let logLinkText = document.createTextNode('Logger');
            logMenuEl.appendChild(logLinkText);
            logMenuEl.href='javascript:void(0);';
            logMenuEl.onclick=function() {onLoggerClicked()};
            
            logMenuTdEl.appendChild(logMenuEl);

            let linkText = document.createTextNode('List Users');
            adminMenuEl.appendChild(linkText);
            adminMenuEl.title='TITLE';
            adminMenuEl.href='javascript:void(0);';
            adminMenuEl.onclick=function() {onAdminMenuClicked()}; 

            adminMenuTdEl.appendChild(adminMenuEl);

            logoutButtonEl = document.getElementById('logout-td');
            logoutButtonEl.before(adminMenuTdEl);
            logoutButtonEl.before(logMenuTdEl);
        }
        onHomepageClicked();
    } else {
        onOtherResponse(loginContentDivEl, this);
    }
}

function onLoginButtonClicked() {
    const loginFormEl = document.forms['login-form'];

    const emailInputEl = loginFormEl.querySelector('input[name="email"]');
    const passwordInputEl = loginFormEl.querySelector('input[name="password"]');

    const email = emailInputEl.value;
    const password = passwordInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'login');
    xhr.send(params);
}

function onRegisterButtonClicked() {
    showContents(['register-content']);
}

function onGoogleSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
   
    const token = id_token = googleUser.getAuthResponse().id_token;

    const params = new URLSearchParams();
    params.append('token', id_token);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'google_login');
    xhr.send(params);
  }

function onBackToLoginButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if(this.status !== 200) {
            showContents(['login-content']);
        } else {
            showContents(['user-menu', 'welcome-content']);
        }
    }
    xhr.open('GET', 'protected/auth');
    xhr.send();
}