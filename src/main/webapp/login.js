let adminMenuTdEl;

function onLoginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        if(user.role == 'ADMIN'){

            adminMenuTdEl = document.createElement('td');
            let adminMenuEl = document.createElement('a');

            let linkText = document.createTextNode("ADMIN MENU");
            adminMenuEl.appendChild(linkText);
            adminMenuEl.title="TITLE";
            adminMenuEl.href="javascript:void(0);";
            adminMenuEl.onclick=function() {alert("ADMIN stuff")}; 

            adminMenuTdEl.appendChild(adminMenuEl);
            document.getElementById("user-menu-tr").appendChild(adminMenuTdEl);
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

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
}

function onRegisterButtonClicked() {
    showContents(['register-content']);
}

