package com.insa.TeamOpsSystem.Task1;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task1")
@RequiredArgsConstructor
public class Task1Controller {
    private final Task1Repository task1Repository;
    @GetMapping()// get or read all data inside task1 table
    private List<Task1> getAll() {
        return task1Repository.findAll();
    }

    @DeleteMapping("/{id}")// delete data by id

    private  void deleteTask1ById(@PathVariable Long id) {
          task1Repository.deleteById(id);
    }
    @PostMapping()//used to add new record
    private Task1 addNewData(@RequestBody Task1 task1) {
        return task1Repository.save(task1);
    }
    @GetMapping("/{id}")// get or read data by id
    private Object getTask1ById(@PathVariable Long id) {
        return task1Repository.findById(id);
    }
}
