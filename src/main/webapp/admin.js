let usersTableEl;
let usersTableBodyEl;


function onAllUsersReceived(){
    if (this.status === OK) {
        showContents(['user-menu', 'users-content']);
        onAllUsersLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(schedulesContentDivEl, this); // needs update 
    }
}

function onAllUsersLoad(users){
    usersTableEl = document.getElementById('users-table');
    usersTableBodyEl = usersTableEl.querySelector('tbody');

    appendUsersTable(users);
}

function appendUsersTable(users) { // 2
    removeAllChildren(usersTableBodyEl);

    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        appendUsers(user);
    }
}

function appendUsers(user) { // 3
    const aEl = document.createElement('a');
    aEl.textContent = user.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.userId = user.id;
    aEl.addEventListener('click', onUserClicked); 

    const idTdEl = document.createElement('td');
    idTdEl.textContent= user.id;

    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);

    const emailTdEl = document.createElement('td');
    emailTdEl.textContent = user.email;

    const roleTdEl = document.createElement('td');
    roleTdEl.textContent = user.role;

    const trEl = document.createElement('tr');

    trEl.appendChild(idTdEl);
    trEl.appendChild(nameTdEl);
    trEl.appendChild(emailTdEl);
    trEl.appendChild(roleTdEl);
    usersTableBodyEl.appendChild(trEl);
} 

function onUserClicked(){
    alert('niceuo');
    //magic
}