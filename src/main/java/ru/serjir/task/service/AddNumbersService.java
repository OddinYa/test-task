package ru.serjir.task.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serjir.task.entity.TableExampleEntity;
import ru.serjir.task.model.RequestModel;
import ru.serjir.task.model.pojo.MyJson;
import ru.serjir.task.repo.ExampleRepo;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class AddNumbersService  {

   
    private int id;
    private long add;

    private static AtomicLong atomicLong = new AtomicLong();


    @Autowired
    private ExampleRepo exampleRepo;

    public MyJson addNumers(RequestModel requestModel) throws Exception {

        id = requestModel.getId();
        add = requestModel.getAdd();

        TableExampleEntity tableExampleEntity = exampleRepo.getReferenceById(id);

        if(tableExampleEntity.getId()!=id){
            throw new Exception();
        }
        atomicLong.set(tableExampleEntity.getMyJson().getCurrent());
        MyJson myJson = tableExampleEntity.getMyJson();

        myJson.setCurrent(atomicLong.addAndGet(add));

        upadateJsonb(tableExampleEntity,myJson);

        return myJson;
    }

    public void upadateJsonb(TableExampleEntity tableExampleEntity, MyJson myJson){

       TableExampleEntity tableExample = new TableExampleEntity();

       tableExample.setId(tableExampleEntity.getId());
       tableExample.setMyJson(myJson);
       exampleRepo.save(tableExample);
    }


}
