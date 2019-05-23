// Basic table functions

function createTable(id, rowNum, header, onClickFunction, onDragFunction, onDropFunction) {
    const tEl = document.createElement("table");
    tEl.setAttribute("id", id);
    tEl.appendChild(_createTableHeader(header, header.length));
    for (let row = 0; row < rowNum; row++) {
        const trEl = document.createElement("tr");
        const td1El = document.createElement("td");
        td1El.innerHTML = row + " - " + (row + 1);
        trEl.appendChild(td1El);
        for (let col = 0; col < header.length; col++) {
            const tdEl = document.createElement("td");
            tdEl.setAttribute("class", "tableField");
            tdEl.setAttribute("draggable", "true");
            tdEl.setAttribute("id", id + "|" + col + "|" + row);
            trEl.appendChild(tdEl);

            if (onClickFunction != null) {
                tdEl.addEventListener("click", onClickFunction);
            }
            if (onDragFunction != null) {
                initDrag();
                tdEl.addEventListener("dragstart", onDragFunction);
            }
            if (onDropFunction != null) {
                tdEl.addEventListener("drop", onDropFunction);
            }
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

// CLASS


class ScheduleTable {
    constructor(tableDomId, schedule, tasks) {
        this.tableDomId = tableDomId;
        this.schedule = schedule;
        this.tasks = tasks;
        this.onFieldClicked = null;
        this.onFieldDragged = null;
        this.onFieldDropped = null;
    }

    generateDom(){
        /*
        Creates and returns a schedule table element that displays tasks associated with a schedule.
        Params: tableDomId: id of the table element to be created
                schedule: a schedule model object
                tasks: a list of task dto objects
                callback: a callback function executed when a table field is beinelementg clicked on.
                    It must contain a result variable, which will be provided as an object
                    with the following fields:
                        task: a task object (null if field is empty)
                        clickedDay
                        clickedHour
        */
        
        const header = this._createHeader();
        const tEl = createTable(this.tableDomId, 24, header, this._bindCallback(this.onFieldClicked, this._clickCallback),
                                                             this._bindCallback(this.onFieldDragged, this._dragCallback),
                                                             this._bindCallback(this.onFieldDropped, this._dropCallback));
        this._printScheduleToTable(tEl);
        return tEl
    }

    _createHeader() {
        let header = [];
        const startingDate = convertDate(this.schedule.startingDate);
        for (let i = 0; i < this.schedule.durationInDays; i++) {
            const date = startingDate.addDays(i);
            header.push(getDateStr(date));
        }
        return header;
    }

    _bindCallback(trigger, callback) {
        let newCallBack;
        if (trigger != null) {
            newCallBack = callback.bind(this);
        } else {
            newCallBack = null;
        }
        return newCallBack;
    }

    _printScheduleToTable(tableEl) {
        const id = tableEl.getAttribute("id");
        for (let i = 0; i < this.tasks.length; i++) {
            const day = this.tasks[i].day;
            const hourStart = this.tasks[i].hourStart;
            const hourEnd = this.tasks[i].hourEnd;
            for (let h = hourStart - 1; h < hourEnd - 1; h++) {
                const tdEl = getTableField(tableEl, day - 1, h + 1);
                this._assignTaskToField(tdEl, this.tasks[i].task, i)
            }
        }
    }
    
    _assignTaskToField(tdEl, task, index) {
        tdEl.textContent = task.title;
        tdEl.setAttribute("taskIndex", index);
    }
    
    _getCallbackResult(res) {
        let el;
        el = res.originalTarget;
        if (el == undefined) {
            el = res.srcElement;
        }
        //el = res.srcElement;
        const coords = getFieldCoords(el);
        const index = el.getAttribute("taskIndex");
        let task;
        if (index == null) {
            task = null;
        } else {
            task =  this.tasks[index];
        }
        const result = {}
        result.task = task;
        result.day = coords[0] + 1;
        result.clickedDay = coords[0] + 1;
        result.clickedHour = coords[1];
        result.element = el;
        return result;
    }

    _clickCallback(res){
        // const el = res.srcElement ** CHROME USES THIS
        const result = this._getCallbackResult(res);
        this.onFieldClicked(result);
    }

    _dragCallback(res) {
        // const el = res.srcElement ** CHROME USES THIS
        const result = this._getCallbackResult(res);
        this.onFieldDragged(result);

    }
    
    _dropCallback(res) {
        // const el = res.srcElement ** CHROME USES THIS
        const result = this._getCallbackResult(res);
        this.onFieldDropped(result);

    }
}
