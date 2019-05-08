Date.prototype.addDays = function(days) {
    var date = new Date(this.valueOf());
    date.setDate(date.getDate() + days);
    return date;
}

function convertDate(localDate) {
    const str = localDate.year + "-" + localDate.monthValue + "-" + localDate.dayOfMonth;
    return new Date(str);
}

function getDateValues(date) {
    const str = date.toJSON();
    const year = str.substr(0, 4);
    const month = str.substr(5, 6);
    const day = str.substr(8, 9);

    alert(month);

}