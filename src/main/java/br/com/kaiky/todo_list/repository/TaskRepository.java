package br.com.kaiky.todo_list.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kaiky.todo_list.entity.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {}
