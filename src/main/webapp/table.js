function createTable(id, rowNum, header, onClickFunction) {
    const tEl = document.createElement("table");
    tEl.setAttribute("id", id);
    tEl.appendChild(createTableHeader(header, header.length));
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

function createTableHeader(header, len) {
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

function setTableField(tableId, col, row, content) {
    const tdEl = getTableField(tableId, col, row);
    tdEl.innerHTML = content;
}

function getTableField(tableId, col, row) {
    return tdEl = document.getElementById(tableId + "|" + col + "|" + row);
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