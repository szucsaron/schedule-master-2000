function createTable(id, array, header, onClickFunction) {
    const columns = array.length;
    const rows = array[0].length;
    const tEl = document.createElement("table");
    tEl.setAttribute("id", id);
    tEl.appendChild(createTableHeader(header));
    for (let row = 0; row < rows; row++) {
        const trEl = document.createElement("tr");
        const td1El = document.createElement("td");
        td1El.innerHTML = row + 1;
        trEl.appendChild(td1El);
        for (let col = 0; col < columns; col++) {
            const tdEl = document.createElement("td");
            tdEl.setAttribute("row", row);
            tdEl.setAttribute("col", col);
            tdEl.innerHTML = array[col][row]
            trEl.appendChild(tdEl);
            tdEl.addEventListener("click", onClickFunction);
        }
        tEl.appendChild(trEl);
    }
    return tEl;
}

function createTableHeader(header) {
    const trEl = document.createElement("tr");
    const tdEl = document.createElement("td");
    trEl.appendChild(tdEl);
    for (let col = 0; col < header.length; col++) {
        const tdEl = document.createElement("td");
        tdEl.innerHTML = header[col];
        trEl.appendChild(tdEl);
    }
    return trEl;
}

function getColIndex(tableFieldEl) {
    return tableFieldEl.getAttribute("col");
}

function getRowIndex(tableFieldEl) {
    return tableFieldEl.getAttribute("row");
}