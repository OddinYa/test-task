package ru.serjir.task.service;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.serjir.task.entity.TableExampleEntity;
import ru.serjir.task.model.RequestModel;
import ru.serjir.task.model.pojo.MyJson;
import ru.serjir.task.repo.ExampleRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;



@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AddNumbersServiceTest {

    @InjectMocks
    private AddNumbersService addNumbersService;
    @MockBean
    private ExampleRepo exampleRepo;

    @Test
    void addTest() throws Exception {
        Mockito.when(exampleRepo.getReferenceById(1)).thenReturn(new TableExampleEntity(1,new MyJson(10L)));
        MyJson expected = new MyJson(20L);
        RequestModel requestModel = new RequestModel(1,10);

        MyJson actual = addNumbersService.addNumers(requestModel);
        Assertions.assertEquals(expected.getCurrent(),actual.getCurrent());
    }

    @Test
    void exceptionTest() {
        try {
            Mockito.when(exampleRepo.getReferenceById(1)).thenReturn(new TableExampleEntity(1,new MyJson(10L)));
            MyJson expected = new MyJson(20L);
            RequestModel requestModel = new RequestModel(2,10);
            MyJson actual = addNumbersService.addNumers(requestModel);
            Assertions.assertTrue(false);
        }catch (Exception e){
            Assertions.assertTrue(true);
        }
    }
    @Test
    void manyEntityTest() throws Exception {
        List<TableExampleEntity> list = new ArrayList<>();
        list.add(new TableExampleEntity(1,new MyJson(10L)));
        list.add(new TableExampleEntity(2,new MyJson(15L)));
        list.add(new TableExampleEntity(3,new MyJson(60L)));

        Mockito.when(exampleRepo.getReferenceById(1)).thenReturn(list.get(0));
        Mockito.when(exampleRepo.getReferenceById(2)).thenReturn(list.get(1));
        Mockito.when(exampleRepo.getReferenceById(3)).thenReturn(list.get(2));

        RequestModel requestModel1 = new RequestModel(1,10);
        RequestModel requestModel2 = new RequestModel(1,50);
        RequestModel requestModel3 = new RequestModel(3,60);


        MyJson expected3 = new MyJson(120L);
        MyJson actual3 = addNumbersService.addNumers(requestModel3);
        Assertions.assertEquals(expected3.getCurrent(),actual3.getCurrent());

        MyJson expected2 = new MyJson(60L);
        MyJson actual2 = addNumbersService.addNumers(requestModel2);
        Assertions.assertEquals(expected2.getCurrent(),actual2.getCurrent());

        MyJson expected1 = new MyJson(70L);
        MyJson actual1 = addNumbersService.addNumers(requestModel1);
        Assertions.assertEquals(expected1.getCurrent(),actual1.getCurrent());
    }
        @Test
        void concurrencyTest() throws InterruptedException{

            RequestModel requestModel1 = new RequestModel(1,10);
            RequestModel requestModel2 = new RequestModel(1,50);
            TableExampleEntity tableExampleEntity = new TableExampleEntity(1,new MyJson(10L));
            Mockito.when(exampleRepo.getReferenceById(1)).thenReturn(tableExampleEntity);

            CompletableFuture first = CompletableFuture.runAsync(()->{
                try {
                    MyJson firstJson = addNumbersService.addNumers(requestModel1);
                    tableExampleEntity.setMyJson(firstJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            CompletableFuture second = CompletableFuture.runAsync(()->{
                try {
                    MyJson secondJson = addNumbersService.addNumers(requestModel2);
                    tableExampleEntity.setMyJson(secondJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            CompletableFuture.allOf(first,second).thenAccept(v ->{
                MyJson expected = new MyJson(70L);
                MyJson actual = tableExampleEntity.getMyJson();

                Assertions.assertEquals(expected.getCurrent(),actual.getCurrent());
            });
    }




}

