package com.codecool.web.dto;



import com.codecool.web.model.Task;

import java.util.List;

public final class TaskDto {

    private final Task task;


    public TaskDto(Task task) {
        this.task = task;

    }

    public Task getTask() {
        return task;
    }


}
