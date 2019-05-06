function onRegisterResponse() {

}

function onRegisterButtonClicked() {
        const loginFormEl = document.forms['register-form'];

        const emailInputEl = loginFormEl.querySelector('input[name="email"]');
        const passwordInputEl = loginFormEl.querySelector('input[name="password"]');
        const rePasswordInputEl = loginFormEl.querySelector('input[name="repassword"]');
        const nameInputEl = loginFormEl.querySelector('input[name="name"]');

        const email = emailInputEl.value;
        const password = passwordInputEl.value;
        const repassword = rePasswordInputEl.value;
        const name = nameInputEl.value;

        const params = new URLSearchParams();
        params.append('email', email);
        params.append('password', password);
        params.append('repassword', repassword);
        params.append('name', name);

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onRegisterResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'register');
        xhr.send(params);

}
