package demo.doc;

import demo.entity.Detail;
import demo.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author yaodw
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "order_details_index",
        shards = 5, replicas = 1, refreshInterval = "-1")
public class OrderDetailsIndex {
    @Id
    private UUID id;
    @Field(type = FieldType.Integer)
    private Integer age;
    @Field(type = FieldType.Text, analyzer = "ik_max_word"
            , searchAnalyzer = "ik_max_word")
    private String bio;
    @Field(type = FieldType.Text, analyzer = "ik_max_word"
            , searchAnalyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Auto, analyzer = "ik_max_word"
            , searchAnalyzer = "ik_max_word")
    private Set<DetailIndex> detail = new HashSet<>();


    public static Set<OrderDetailsIndex> orderToIndex(Collection<Orders> orders) {
        Set<OrderDetailsIndex> set = new HashSet<>();
        orders.forEach(order -> {
            OrderDetailsIndex orderDetailsIndex = OrderDetailsIndex.builder()
                    .id(order.getId())
                    .age(18).bio("bio")
                    .name(order.getName())
                    .detail(OrderDetailsIndex.detailToIndex(order.getDetails()))
                    .build();
            set.add(orderDetailsIndex);
        });
        return set;
    }

    public static Set<DetailIndex> detailToIndex(Collection<Detail> details) {
        Set<DetailIndex> set = new HashSet<>();
        details.forEach(detail -> {
            DetailIndex detailIndex = DetailIndex.builder()
                    .id(detail.getId())
                    .name(detail.getName())
                    .build();
            set.add(detailIndex);
        });
        return set;
    }

    @Builder
    private static class DetailIndex {
        @Field(type = FieldType.Auto)
        private UUID id;
        @Field(type = FieldType.Text, analyzer = "ik_max_word"
                , searchAnalyzer = "ik_max_word")
        private String name;
    }

}