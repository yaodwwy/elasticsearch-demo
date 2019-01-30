package demo.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "syno_index",
        shards = 1, replicas = 0, refreshInterval = "-1")
public class SynoIndex {
    @Id
    private UUID id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word",
            searchAnalyzer = "ik_max_word")
    private String name;
}
