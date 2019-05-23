function onLogoutResponse() {
    if (this.status === OK) {
        if (JSON.parse(localStorage.getItem('user')).role == 'ADMIN') {
            document.getElementById("user-menu-tr").removeChild(adminMenuTdEl);
        }
        setUnauthorized();
        clearMessages();
        showContents(['login-content'])
    } else {
        onOtherResponse(logoutContentDivEl, this);
    }
}

function onLogoutButtonClicked(event) {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLogoutResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/logout');
    xhr.send();
}
