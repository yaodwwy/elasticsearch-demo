package demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

/**
 * @author yaodw
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsLastTimestamp {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Date last;

}
