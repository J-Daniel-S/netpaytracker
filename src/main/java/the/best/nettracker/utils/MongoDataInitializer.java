package the.best.nettracker.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import the.best.nettracker.documents.User;
import the.best.nettracker.services.UserService;

@Component
public class MongoDataInitializer implements CommandLineRunner {

	@Autowired
	private UserService usRepo;

	@Override
	public void run(String... args) throws Exception {
		String jsonUser = "{\"name\":\"curly jefferson\",\"username\":\"curly jefferson\",\"password\":\"$2y$10$EHm8KsP4lwholAstwXqbzObUgRK84gkivkUH.5Y7a5lxT28FQuIsi\",\"taxRate\":\"0.18\",\"jobs\":[{\"name\":\"cheesegrater\",\"rate\":23.07,\"payPeriods\":[{\"dateName\":\"07-04-2020\",\"shifts\":[{\"date\":\"07-06\",\"hours\":8,\"grossPay\":184.56,\"netPay\":151.34,\"taxes\":33.22},{\"date\":\"07-07\",\"hours\":5,\"grossPay\":115.35,\"netPay\":94.56,\"taxes\":20.76},{\"date\":\"07-08\",\"hours\":8,\"grossPay\":184.56,\"netPay\":151.34,\"taxes\":33.22},{\"date\":\"07-10\",\"hours\":8,\"grossPay\":184.56,\"netPay\":151.34,\"taxes\":33.22}],\"netPay\":548.61,\"grossPay\":669.04,\"taxes\":120.43},{\"dateName\":\"07-12-2020\",\"shifts\":[{\"date\":\"07-13\",\"hours\":8,\"grossPay\":184.56,\"netPay\":151.34,\"taxes\":33.22},{\"date\":\"07-15\",\"hours\":5,\"grossPay\":115.35,\"netPay\":94.56,\"taxes\":20.76},{\"date\":\"07-16\",\"hours\":8,\"grossPay\":184.56,\"netPay\":151.34,\"taxes\":33.22},{\"date\":\"07-17\",\"hours\":8,\"grossPay\":184.56,\"netPay\":151.34,\"taxes\":33.22}],\"netPay\":548.61,\"grossPay\":669.04,\"taxes\":120.43}]},{\"name\":\"tentstaker\",\"rate\":40,\"payPeriods\":[{\"dateName\":\"07-04-2020\",\"shifts\":[{\"date\":\"07-06\",\"hours\":6,\"grossPay\":240,\"netPay\":151.34,\"taxes\":33.22},{\"date\":\"07-07\",\"hours\":5,\"grossPay\":200,\"netPay\":94.56,\"taxes\":20.76},{\"date\":\"07-10\",\"hours\":3,\"grossPay\":120,\"netPay\":151.34,\"taxes\":33.22}],\"netPay\":560,\"grossPay\":459.2,\"taxes\":100.8}]}],\"role\":\"USER\",\"_class\":\"the.best.nettracker.documents.User\"}";
		
		ObjectMapper mapper = new ObjectMapper();
		Optional<User> user = usRepo.findByUsername("curly jefferson");
		
		User curly = mapper.readValue(jsonUser, User.class);
		
		if (user.isEmpty()) {
			usRepo.save(curly);
			System.out.println("User curly jefferson saved to MongoDB");
		} else {
			System.out.println("User curly jefferson already present in MongoDB");
		}
		
		
		
	}

	

}
