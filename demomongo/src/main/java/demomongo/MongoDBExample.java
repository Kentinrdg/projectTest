package demomongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import model.MyData;

@Component
public class MongoDBExample {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoDBExample(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void insertData() {
    	mongoTemplate.createCollection("EP");
        // Création d'un objet pour représenter les données à insérer
        MyData data = new MyData("foo", "bar");

        // Insertion des données dans la collection "myCollection"
        mongoTemplate.insert(data, "EP");
    }
}
