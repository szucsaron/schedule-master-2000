const xhr = new XMLHttpRequest();
xhr.onload = function() {
   /* if(this.status !== 200) {
        showContents(['login-content']);
    } else {
        showContents(['user-menu', 'welcome-content']);
    }
*/
    showContents(['login-content']);
}
xhr.open('GET', 'protected/auth');
xhr.send();