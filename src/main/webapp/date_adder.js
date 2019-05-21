Date.prototype.addDays = function(days) {
    var date = new Date(this.valueOf());
    date.setDate(date.getDate() + days);
    return date;
}

function convertDate(localDate) {
    const str = localDate.year + "-" + localDate.monthValue + "-" + localDate.dayOfMonth;
    return new Date(str);
}

function adjustDateValForDigits(value) {
    if (value < 10) {
        return "0" + String(value);
    } else {
        return String(value)
    }
}

function getDateStr(date) {
    let day = adjustDateValForDigits(date.getDate());
    let month = adjustDateValForDigits(date.getMonth() + 1);
    let year = adjustDateValForDigits(date.getFullYear());
    return day + "." + month + "." + year;
}
