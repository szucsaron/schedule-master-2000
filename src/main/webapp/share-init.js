function onLoad() {
    setScheduleAsPublic();
    const scheduleEl = document.getElementById('schedule-content');

    const scheduleId = scheduleEl.getAttribute('schedule_id');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSharedScheduleResponse);
    xhr.addEventListener('error', onNetworkError);

    const params = new URLSearchParams();
    params.append('schedule_id', scheduleId);
    params.append('fetch', '1');

    xhr.open('GET', 'share?' + params.toString());
    xhr.send();
}

function onSharedScheduleResponse() {
    const scheduleDto = JSON.parse( this.responseText);
    const scheduleTable = new ScheduleTable('schedule-table', scheduleDto.schedule, scheduleDto.taskDto);
    const scheduleEl = document.getElementById('schedule-content');
    scheduleEl.appendChild(scheduleTable.generateDom());
}