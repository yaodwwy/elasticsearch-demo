package demo.doc;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.UUID;

/**
 * @author yaodw
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "person_index",
        shards = 5, replicas = 1, refreshInterval = "-1")
public class PersonIndex {
    @Id
    private UUID id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word",
            searchAnalyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Integer)
    private Integer age;
    @Field(type = FieldType.Text, analyzer = "ik_max_word"
            , searchAnalyzer = "ik_max_word")
    private String bio;
}
