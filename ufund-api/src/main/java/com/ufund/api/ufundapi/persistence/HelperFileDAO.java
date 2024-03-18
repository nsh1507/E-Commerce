package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Helper;


@Component
public class HelperFileDAO implements HelperDAO {
    private static final Logger LOG = Logger.getLogger(HelperFileDAO.class.getName());
    Map<String,Helper> helpers;   // Provides a local cache of the helper objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Helper
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Helper File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public HelperFileDAO(@Value("${helpers.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the helpers from the file
    }

    /**
     * Generates an array of {@linkplain Helper helpers} from the tree map
     * used to be private, changing to public for testing
     * @return  The array of {@link Helper helpers}, may be empty
     */
    public Helper[] getHelpersArray() {
        ArrayList<Helper> helperArrayList = new ArrayList<>();

        for (Helper helper : helpers.values()) {
            helperArrayList.add(helper);
        }

        Helper[] helperArray = new Helper[helperArrayList.size()];
        helperArrayList.toArray(helperArray);
        return helperArray;
    }

    /**
     * Saves the {@linkplain Helper helpers} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Helper helpers} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    boolean save() throws IOException {
        Helper[] helperArray = getHelpersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),helperArray);
        return true;
    }

    /**
     * Loads {@linkplain Helper helpers} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    boolean load() throws IOException {
        helpers = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of helpers
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Helper[] helperArray = objectMapper.readValue(new File(filename),Helper[].class);

        // Add each helper to the tree map
        for (Helper helper : helperArray) {
            helpers.put(helper.getUsername(),helper);
        }

        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Helper[] getHelpers() {
        synchronized(helpers) {
            return getHelpersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Helper getHelper(String username) {
        synchronized(helpers) {
            if (helpers.containsKey(username))
                return helpers.get(username);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Helper loginHelper(String username, String password) {
        synchronized(helpers) {
            if (helpers.containsKey(username)) {
                if (helpers.get(username).getPassword().equals(password)) {
                    return helpers.get(username);
                }
            }
            
            return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Helper createHelper(Helper helper) throws IOException {
        synchronized(helpers) {
            // We create a new helper object because the username field is immutable
            Helper newHelper = new Helper(helper.getUsername(),helper.getPassword());
            helpers.put(newHelper.getUsername(),newHelper);
            save(); // may throw an IOException
            return newHelper;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Helper updateHelper(Helper helper) throws IOException {
        synchronized(helpers) {
            if (helpers.containsKey(helper.getUsername()) == false)
                return null;  // helper does not exist

            helpers.put(helper.getUsername(),helper);
            save(); // may throw an IOException
            return helper;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteHelper(String username) throws IOException {
        synchronized(helpers) {
            if (helpers.containsKey(username)) {
                helpers.remove(username);
                return save();
            }
            else
                return false;
        }
    }
}