package demo.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Document(indexName = "syno_index",
        shards = 5, replicas = 1, refreshInterval = "-1")
@Setting(settingPath = "elasticsearch/settings.json")
public class SynoIndex {
    @Id
    private UUID id;
    @Field(type = FieldType.Text, analyzer = "ik_all",
            searchAnalyzer = "ik_all")
    private String name;
}
