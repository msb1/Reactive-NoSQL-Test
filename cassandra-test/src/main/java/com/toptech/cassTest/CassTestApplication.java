package com.toptech.cassTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptech.cassTest.model.*;
import com.toptech.cassTest.service.DataService;
import com.toptech.cassTest.service.EquipService;
import com.toptech.cassTest.service.StatsService;
import com.toptech.cassTest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class CassTestApplication implements ApplicationRunner {

	private static ObjectMapper mapper = new ObjectMapper();
	private static Logger logger = LoggerFactory.getLogger(CassTestApplication.class);

	@Autowired
	private UserService userService;

	@Autowired
	private DataService dataService;

	@Autowired
	private StatsService statsService;

	@Autowired
	private EquipService equipService;

	public static void main(String[] args) {
		logger.info("STARTING : application...");
		SpringApplication.run(CassTestApplication.class, args);
		logger.info("STOPPING : application...");
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {

		// delete all users
		userService.deleteAll().subscribe();
		System.out.println("*************************************************");

		// create three dummy users (saved to MongoDB) for testing
		User user1 = new User("barnwaldo", "1234", "Barn", "Waldo",
				"barnwaldo@gmail.com", true, Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER));
		userService.createUser(user1).subscribe();
		System.out.println(user1.toString());

		User user2 = new User("jrchuck", "5678", "Johnson", "Rahbeck",
				"jrchuck@yahoo.com", true, Arrays.asList(Role.ROLE_ADMIN));
		userService.createUser(user2).subscribe();
		System.out.println(user2.toString());

		User user3 = new User("nice", "asdf", "Nic", "Eps",
				"nickei@msn.com", true, Arrays.asList(Role.ROLE_USER));
		userService.createUser(user3).subscribe();
		System.out.println(user3.toString());

		// get all users from toptech db users collection
		Flux<User> userFlux = userService.getAllUsers();
		List<User> users = userFlux.collectList().block();
		users.forEach(System.out::println);
		System.out.println("*************************************************");

		Flux<DataRecord> dataRecordFlux = dataService.getAllDataRecords();
		List<DataRecord> dataRecords = dataRecordFlux.collectList().block();

		System.out.println("Number of data records: " + dataRecords.size());
		for (int i = 0; i < 5; i++) {
			System.out.println(dataRecords.get(i));
		}

		System.out.println("*************************************************");
		ObjectMapper mapper = new ObjectMapper();
		Stats stat1 = new Stats();
		stat1.setEquipmentName("widgetA");
		stat1.setTotalCounts(345L);
		stat1.setLabelCounts(Stream.of(new Object[][] {
				{ "lb1", 300L },
				{ "lb2", 45L },
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Long) data[1])));

		stat1.setDiscreteCounts(Stream.of(new Object[][] {
				{ "d0", mapper.writeValueAsString(Arrays.asList(10L, 20L, 30L)) },
				{ "d1", mapper.writeValueAsString(Arrays.asList(11L, 21L)) },
				{ "d2", mapper.writeValueAsString(Arrays.asList(12L, 22L, 32L, 42L, 52L)) },
				{ "d3", mapper.writeValueAsString(Arrays.asList(13L, 23L, 33L, 43L)) },
				{ "d4", mapper.writeValueAsString(Arrays.asList(14L, 24L)) },
				{ "d5", mapper.writeValueAsString(Arrays.asList(15L, 25L)) },
				{ "d6", mapper.writeValueAsString(Arrays.asList(16L, 26L, 36L)) }
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1])));

		stat1.setMeans(Stream.of(new Object[][] {
				{ "s0", 8.25 },
				{ "s1", 1.25 },
				{ "s2", 4.56 },
				{ "s3", 4.67 },
				{ "s4", 9.45 },
				{ "s5", 83.6 },
				{ "s6", 101.0 },
				{ "s7", 801.1 },
				{ "s8", 45.3 },
				{ "s9", 67.5 }
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1])));

		stat1.setStdDev(Stream.of(new Object[][] {
				{ "s0", 0.25 },
				{ "s1", 0.25 },
				{ "s2", 0.56 },
				{ "s3", 0.67 },
				{ "s4", 0.45 },
				{ "s5", 3.6 },
				{ "s6", 1.0 },
				{ "s7", 1.1 },
				{ "s8", 5.3 },
				{ "s9", 7.5 }
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1])));

		stat1.setUpper(Stream.of(new Object[][] {
				{ "s0", 100.0 },
				{ "s1", 100.0 },
				{ "s2", 100.0 },
				{ "s3", 100.0 },
				{ "s4", 100.0 },
				{ "s5", 100.0 },
				{ "s6", 100.0 },
				{ "s7", 100.0 },
				{ "s8", 100.0 },
				{ "s9", 100.0 }
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1])));

		stat1.setLower(Stream.of(new Object[][] {
				{ "s0", 10.0 },
				{ "s1", 10.0 },
				{ "s2", 10.0 },
				{ "s3", 10.0 },
				{ "s4", 10.0 },
				{ "s5", 10.0 },
				{ "s6", 10.0 },
				{ "s7", 10.0 },
				{ "s8", 10.0 },
				{ "s9", 10.0 }
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1])));

		System.out.println(stat1);
		statsService.createStats(stat1).subscribe();
		System.out.println(statsService.getStats("widgetA").block());

		System.out.println("*************************************************");

		Map<String, String> cats = Stream.of(new Object[][] {
				{ "d0", mapper.writeValueAsString(Arrays.asList(1, 2, 3)) },
				{ "d1", mapper.writeValueAsString(Arrays.asList("Good", "Bad")) },
				{ "d2", mapper.writeValueAsString(Arrays.asList(1, 2, 3, 4, 5)) },
				{ "d3", mapper.writeValueAsString(Arrays.asList("ugly", "bad", "neutral", "great")) },
				{ "d4", mapper.writeValueAsString(Arrays.asList("Good", "Bad")) },
				{ "d5", mapper.writeValueAsString(Arrays.asList("Good", "Bad")) },
				{ "d6", mapper.writeValueAsString(Arrays.asList(1, 2, 3)) }
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));

		Equipment equip = new Equipment("widgetA", Arrays.asList("Good", "Bad"),
				Arrays.asList("s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9"), cats);
		System.out.println(equip);
		equipService.createEquipment(equip).subscribe();
		System.out.println(equipService.getEquipment("widgetA").block());
	}
}
