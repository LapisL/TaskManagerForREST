package com.task.backend.task.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.backend.common.entity.task.TaskEntity;
import com.task.backend.task.domain.TaskRepository;

@Service
@Transactional
public class TaskService {
	
	@Autowired
    TaskRepository taskRepository;

	//データの新規登録
	public TaskEntity registTask(TaskEntity data) {

        TaskEntity task = taskRepository.save(data);

        return task;
}
	
	//タスクをListで取得するためのメソッド
    public List<TaskEntity> getAll(){
        return taskRepository.findAll();
    }
    
    ////★


	//タスクをTaskIdから呼び出すためのメソッド
    public TaskEntity getTask(int taskId){
        return taskRepository.findByTaskId(taskId);
    }

	//タスクを更新するメソッド
    public TaskEntity updateTask(TaskEntity task){
        return taskRepository.save(task);
    }

    //タスクを完了するときのメソッド
    public TaskEntity completeTask(TaskEntity task){
        return taskRepository.save(task);
    }



	//タスクを削除するメソッド
    public void deleteTask(TaskEntity task){
        taskRepository.delete(task);
    }
}

