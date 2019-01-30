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
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "syno_index",
        shards = 5, replicas = 1, refreshInterval = "-1")
@Setting(settingPath = "elasticsearch/settings.json")
public class SynoIndex {
    @Id
    private UUID id;
    @Field(type = FieldType.Text, analyzer = "my_synonyms",
            searchAnalyzer = "my_synonyms")
    private String name;
}
