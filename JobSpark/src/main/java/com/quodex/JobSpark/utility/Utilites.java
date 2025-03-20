package com.quodex.JobSpark.utility;

import com.quodex.JobSpark.entity.Sequence;
import com.quodex.JobSpark.exception.JobSparkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling database-related operations, such as generating sequence numbers.
 */
@Component
public class Utilites {

    // MongoOperations instance for interacting with MongoDB
    private static MongoOperations mongoOperations;

    /**
     * Setter method to initialize the MongoOperations instance using dependency injection.
     *
     * @param mongoOperations - The MongoDB operations instance.
     */
    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        Utilites.mongoOperations = mongoOperations;
    }

    /**
     * Retrieves the next sequence value for a given key from the database.
     *
     * @param key - The unique identifier for the sequence.
     * @return Long - The next sequence value.
     * @throws JobSparkException - If unable to retrieve the sequence.
     */
    public static Long getNextSequence(String key) throws JobSparkException {
        // Create a query to find the sequence document by key
        Query query = new Query(Criteria.where("_id").is(key));

        // Define an update operation to increment the sequence value by 1
        Update update = new Update();
        update.inc("seq", 1);

        // Set options to return the updated sequence value after modification
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        // Find and update the sequence document
        Sequence sequence = mongoOperations.findAndModify(query, update, options, Sequence.class);

        // Throw an exception if no sequence document is found
        if (sequence == null) {
            throw new JobSparkException("Unable to get sequence id for key: " + key);
        }

        // Return the updated sequence value
        return sequence.getSeq();
    }
}
