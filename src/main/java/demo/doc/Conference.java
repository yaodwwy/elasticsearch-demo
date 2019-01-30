/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo.doc;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

/**
 * @author Artur Konczak
 * @author Oliver Gierke
 * @auhtor Christoph Strobl
 */
@Data
@Builder
@Document(indexName = "conference_index",
        shards = 5, replicas = 1, refreshInterval = "-1")
public class Conference {
    private @Id
    String id;
    private String name;
    private @Field(type = Date)
    String date;
    private GeoPoint location;
    private List<String> keywords;
}
