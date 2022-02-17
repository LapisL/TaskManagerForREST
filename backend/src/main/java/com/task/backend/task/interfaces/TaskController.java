package com.task.backend.task.interfaces;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.task.backend.common.entity.task.TaskEntity;
import com.task.backend.task.application.TaskService;
import com.task.backend.task.domain.TaskFactory;
import com.task.backend.task.domain.request.TaskRequest;
import com.task.backend.task.domain.response.TaskResponse;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskFactory taskFactory;

    //全件取得
	@GetMapping("/list")
	public List<TaskResponse> getTaskList() {
		
		return taskFactory.createTaskListResponse(taskService.getAll());
	}

	// // タスクを新規登録するときの処理
	@PostMapping("/regist")
	public TaskResponse registTask(@RequestBody TaskRequest taskRequest) {

		TaskEntity task = new TaskEntity();

		task.setTaskId(taskRequest.getId());
		task.setTaskName(taskRequest.getName());
		if(taskRequest.getDate() != null){
			task.setTaskDate(Date.valueOf(taskRequest.getDate()));
		}
		if(taskRequest.getTime() != null){
			if(taskRequest.getTime().length() < 8){
				taskRequest.setTime(taskRequest.getTime() + ":00");
				task.setTaskTime(Time.valueOf(taskRequest.getTime()));
			}
		}
		task.setTaskPlace(taskRequest.getPlace());
		
		return taskFactory.createTaskResponse(taskService.registTask(task));
	}


	//　詳細情報取得
	@GetMapping("/{taskId}")
    public TaskResponse getTask(@PathVariable int taskId){
		return taskFactory.createTaskResponse(taskService.getTask(taskId));
	}


	// タスクを更新するときの処理
	@PutMapping("/update")
	public TaskResponse updateTask(@RequestBody TaskRequest taskRequest){
		TaskEntity task = taskService.getTask(taskRequest.getId());
		task.setTaskName(taskRequest.getName());
		if(taskRequest.getDate() != null){
			task.setTaskDate(Date.valueOf(taskRequest.getDate()));
		}
		if(taskRequest.getTime() != null){
			if(taskRequest.getTime().length() < 8){
				taskRequest.setTime(taskRequest.getTime() + ":00");
				task.setTaskTime(Time.valueOf(taskRequest.getTime()));
			}
		}
		task.setTaskPlace(taskRequest.getPlace());
		task.setCompleteFlag(taskRequest.isComplete());
		return taskFactory.createTaskResponse(taskService.updateTask(task));
	}

	//タスクを削除
	@DeleteMapping("/delete")
	public void deleteTask(@RequestBody TaskRequest taskRequest){
		TaskEntity deleteTask = taskService.getTask(taskRequest.getId());
		taskService.deleteTask(deleteTask);

	}


	//タスクを完了
	@PutMapping("/complete/{taskId}")
	public TaskResponse completeTask(@PathVariable int taskId){
		TaskEntity task = taskService.getTask(taskId);
		task.setCompleteFlag(!task.isCompleteFlag());
		return taskFactory.createTaskResponse(taskService.completeTask(task));
	}
}