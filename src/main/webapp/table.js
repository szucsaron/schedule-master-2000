// Basic table functions

function createTable(id, rowNum, header, onClickFunction) {
    const tEl = document.createElement("table");
    tEl.setAttribute("id", id);
    tEl.appendChild(_createTableHeader(header, header.length));
    for (let row = 0; row < rowNum; row++) {
        const trEl = document.createElement("tr");
        const td1El = document.createElement("td");
        td1El.innerHTML = row + 1;
        trEl.appendChild(td1El);
        for (let col = 0; col < header.length; col++) {
            const tdEl = document.createElement("td");
            tdEl.setAttribute("class", "tableField");
            tdEl.setAttribute("row", row);
            tdEl.setAttribute("col", col);
            tdEl.setAttribute("id", id + "|" + col + "|" + row);
            trEl.appendChild(tdEl);
            tdEl.addEventListener("click", onClickFunction);
        }
        tEl.appendChild(trEl);
    }
    return tEl;
}

function setTableField(tableId, col, row, content) {
    const tdEl = getTableField(tableId, col, row);
    tdEl.innerHTML = content;
}

function getTableField(tableEl, col, row) {
    rows = tableEl.getElementsByTagName("tr");
    cols = rows[row + 1].getElementsByTagName("td");    
    return cols[col + 1];
}

function getFieldCoords(fieldEl) {
    const id = fieldEl.getAttribute("id");
    const params = id.split("|");
    const x = parseInt(params[1])
    const y = parseInt(params[2]);
    return [x, y];
}

function clearTable(tableEl) {
    const tdEls = tableEl.getElementsByClassName("tableField");
    for (let i = 0; i < tdEls.length; i++) {
        tdEls[i].innerHTML = "";
    }
}

function _createTableHeader(header, len) {
    const trEl = document.createElement("tr");
    const tdEl = document.createElement("td");
    trEl.appendChild(tdEl);
    for (let col = 0; col < len; col++) {
        const tdEl = document.createElement("td");
        tdEl.innerHTML = header[col];
        trEl.appendChild(tdEl);
    }
    return trEl;
}


// Schedule table functions

let _gScheduleTableCallback;
let _gScheduleTableTasks;

function createScheduleTable(tableDomId, schedule, tasks, callback){
    /*
    Create and return a schedule table element that displays tasks in schedule.
    Params: tableDomId: id of the table element to be created
            schedule: a schedule model object
            tasks: a list of task dto objects
            callback: a callback function executed when a table field is being clicked.
                It is returned with a result object containing the following fields:
                    task: a task object (null if field is empty)
                    clickedDay
                    clickedHour
    */


    _gScheduleTableCallback = callback;
    _gScheduleTableTasks = tasks;
    let tEl = document.getElementById("scheduleTable");
    if (tEl != null) {
        tEl.remove();
    }
    header = ["Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7"];
    tEl = createTable(tableDomId, 24, header, _onHourClicked);
    clearTable(tEl);
    _printScheduleToTable(tEl, tasks);
    return tEl
}

function updateScheduleTable(tableEl, tasks) {
    /*
    Clears and updates schedule table with the given task imputs
    Params: tableEl: dom element of the table
            tasks: a list of task dto objects
    */

    clearTable(tableEl);
    _printScheduleToTable(tableEl, tasks);
}

function _printScheduleToTable(tableEl, tasks) {
    const id = tableEl.getAttribute("id");
    for (let i = 0; i < tasks.length; i++) {
        const day = tasks[i].day;
        const hourStart = tasks[i].hourStart;
        const hourEnd = tasks[i].hourEnd;

        
        // alert(tableEl.getElementsByTagName("tr")[0]);
        // alert(tableEl.getElementsByTagName("tr")[0]);
        for (let h = hourStart - 1; h < hourEnd - 1; h++) {
            const tdEl = getTableField(tableEl, day - 1, h);
            _assignTaskToField(tdEl, tasks[i], i)
        }
    }
}

function _assignTaskToField(tdEl, task, index) {
    console.log(tdEl)
    tdEl.textContent = task.title;
    tdEl.setAttribute("taskIndex", index);
}

function _onHourClicked(){
    const coords = getFieldCoords(this);
    const index = this.getAttribute("taskIndex");
    let task;
    if (index == null) {
        task = null;
    } else {
        task = _gScheduleTableTasks[index];
    }
    const result = {}
    result.task = task;
    result.day = coords[0] + 1;
    result.clickedDay = coords[0] + 1;
    result.clickedHour = coords[1] + 1;
    _gScheduleTableCallback(result);
}
