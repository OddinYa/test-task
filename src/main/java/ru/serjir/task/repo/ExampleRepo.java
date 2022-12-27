package ru.serjir.task.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.serjir.task.entity.TableExampleEntity;




@Repository
public interface ExampleRepo extends JpaRepository<TableExampleEntity, Integer> {

}
