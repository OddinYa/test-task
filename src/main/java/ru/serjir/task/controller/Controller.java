package ru.serjir.task.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import ru.serjir.task.model.RequestModel;
import ru.serjir.task.model.pojo.MyJson;
import ru.serjir.task.service.AddNumbersService;

@RestController
public class Controller {

    @Autowired
    AddNumbersService addNumbers;

    @PostMapping(value = "/modify")
    public ResponseEntity modify(@RequestBody RequestModel requestModel) {
        try {

         MyJson myJson = addNumbers.addNumers(requestModel);

            return ResponseEntity.ok().body(myJson);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }

    }

}