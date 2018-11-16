package com.juan.task.infrastructure.cron;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.juan.task.domain.model.Task;
import com.juan.task.infrastructure.persistence.jpa.TaskRepository;

@Configuration
@EnableScheduling
public class TaskSchedule {
	private Logger LOG = LoggerFactory.getLogger(TaskSchedule.class);
	
	@Autowired
	private TaskRepository taskRepository;

	@Scheduled(fixedDelay = 3000, initialDelay = 3000)
	public void taskStatusSchedule() {
		LOG.info("Checking for pending tasks " + new Date());
		
		List<Task> pendings = taskRepository.findPendingPastDateTime(new Date());
		pendings.forEach(task -> setStatusDone(task));
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	protected void setStatusDone(Task task) {
		LOG.info("Changing status to done for task {}", task);
		task.setStatus("done");
		taskRepository.update(task);
	}
}
