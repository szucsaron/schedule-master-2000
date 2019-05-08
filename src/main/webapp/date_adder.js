Date.prototype.addDays = function(days) {
    var date = new Date(this.valueOf());
    date.setDate(date.getDate() + days);
    return date;
}

function convertDate(localDate) {
    const str = localDate.year + "-" + localDate.monthValue + "-" + localDate.dayOfMonth;
    return new Date(str);
}
