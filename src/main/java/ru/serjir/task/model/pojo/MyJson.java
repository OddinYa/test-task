package ru.serjir.task.model.pojo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class MyJson implements Serializable {

    private Long current;

    public MyJson(){

    }

}
