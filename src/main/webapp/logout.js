function onLogoutResponse() {
    if (this.status === OK) {
        if (JSON.parse(localStorage.getItem('user')).role == 'ADMIN') {
            document.getElementById("user-menu-tr").removeChild(adminMenuTdEl);
            document.getElementById("user-menu-tr").removeChild(logMenuTdEl);
        }
        setUnauthorized();
        clearMessages();
        showContents(['login-content'])
    } else {
        onOtherResponse(logoutContentDivEl, this);
    }
}

function onLogoutButtonClicked(event) {

    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLogoutResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/logout');
    xhr.send();
}
