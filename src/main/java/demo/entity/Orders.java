package demo.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author yaodw
 */
@Entity
@Data
@EqualsAndHashCode(exclude = {"details"})
@ToString(exclude = {"details"})
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "Orders.details",
        attributeNodes = @NamedAttributeNode("details"))
public class Orders {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Date time;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "orders")
    private Set<Detail> details = new HashSet<>();
    @Bean
    public CommandLineRunner entitybean() {
        return (args) -> {
            System.out.println("Entity bean");
        };
    }
    public static void main(String[] args) {
        System.out.println(new Date(0));
    }
}
