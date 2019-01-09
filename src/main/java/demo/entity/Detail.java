package demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author yaodw
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detail {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;
}
