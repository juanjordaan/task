package com.juan.task.infrastructure.persistence.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.juan.task.domain.model.Task;

@Repository
public class TaskRepository extends AbstractJpaRepository<Task, Long> {

	public List<Task> findAllByUserId(Long userId) {
		return em.createNamedQuery("TASK_LISTBYUSER", Task.class).setParameter("userId", userId).getResultList();
	}
	
	public List<Task> findPendingPastDateTime(Date date) {
		return em.createNamedQuery("TASK_LISTPENDINGPASTDATETIME", Task.class).setParameter("dateTime", date).getResultList();
	}
}
