let adminMenuTdEl;

function onLoginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        if(user.role == 'ADMIN'){

            adminMenuTdEl = document.createElement('td');
            let adminMenuEl = document.createElement('a');

            let linkText = document.createTextNode("List Users");
            adminMenuEl.appendChild(linkText);
            adminMenuEl.title="TITLE";
            adminMenuEl.href="javascript:void(0);";
            adminMenuEl.onclick=function() {onAdminMenuClicked()}; 

            adminMenuTdEl.appendChild(adminMenuEl);

            logoutButtonEl = document.getElementById('logout-td');
            logoutButtonEl.before(adminMenuTdEl);
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

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
    const token = id_token = googleUser.getAuthResponse().id_token;
    console.log('Token: ' + id_token);

    const params = new URLSearchParams();
    params.append('token', id_token);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'google_login');
    xhr.send(params);
  }
  