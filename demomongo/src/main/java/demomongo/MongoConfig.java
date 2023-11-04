package demomongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoClient create = MongoClients.create("mongodb://localhost:27017");
		MongoTemplate mongoTemplate = new MongoTemplate(create, "local");
		// Vérifie si la collection "users" existe
		if (!mongoTemplate.collectionExists("users")) {
			// Crée la collection "users" avec les options souhaitées
			mongoTemplate.createCollection("users", CollectionOptions.empty().size(100000).capped());
		}

		return mongoTemplate;
	}
}