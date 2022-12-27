package ru.serjir.task.entity;



import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.serjir.task.model.pojo.MyJson;


@Entity
@Getter
@Setter
@Table(name = "sk_example_table")
public class TableExampleEntity {


   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   @Id
   private int id;

   @JdbcTypeCode(SqlTypes.JSON)
   @Column(name = "obj", columnDefinition = "jsonb")
   @NonNull
   private MyJson myJson;

   public TableExampleEntity(){

   }
   public TableExampleEntity(int id,MyJson myJson){
      this.id = id;
      this.myJson = myJson;
   }


}
