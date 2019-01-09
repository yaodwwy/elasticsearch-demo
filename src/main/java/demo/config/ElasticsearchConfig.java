package demo.config;

import demo.doc.Conference;
import demo.doc.OrderIndex;
import demo.doc.PersonIndex;
import demo.repo.ConferenceRepository;
import demo.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

/**
 * @author yaodw
 */
@Configuration
public class ElasticsearchConfig {

    @Autowired
    private ElasticsearchOperations operations;

    @Autowired
    private ConferenceRepository conferenceRepository;
    @Autowired
    private PersonRepository personRepository;

    @PreDestroy
    public void deleteIndex() {
//        operations.deleteIndex(Conference.class);
//        operations.deleteIndex(OrderIndex.class);
//        operations.deleteIndex(PersonIndex.class);
    }

    @PostConstruct
	public void insertDataSample() {

		// Remove all documents
        conferenceRepository.deleteAll();
		operations.refresh(Conference.class);

		// Save data sample
        conferenceRepository.save(Conference.builder().date("2014-11-06").name("Spring eXchange 2014 - London")
				.keywords(Arrays.asList("java", "spring")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        conferenceRepository.save(Conference.builder().date("2014-12-07").name("Scala eXchange 2014 - London")
				.keywords(Arrays.asList("scala", "play", "java")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        conferenceRepository.save(Conference.builder().date("2014-11-20").name("Elasticsearch 2014 - Berlin")
				.keywords(Arrays.asList("java", "elasticsearch", "kibana")).location(new GeoPoint(52.5234051D, 13.4113999))
				.build());
        conferenceRepository.save(Conference.builder().date("2014-11-12").name("AWS London 2014")
				.keywords(Arrays.asList("cloud", "aws")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        conferenceRepository.save(Conference.builder().date("2014-10-04").name("JDD14 - Cracow")
				.keywords(Arrays.asList("java", "spring")).location(new GeoPoint(50.0646501D, 19.9449799)).build());
	}
}
