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
    Map<Integer,Helper> helpers;   // Provides a local cache of the helper objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Helper
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new helper
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
        load();  // load the helper from the file
    }

    /**
     * Generates the next id for a new {@linkplain Helper helper}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Helper helpers} from the tree map
     * 
     * @return  The array of {@link Helper helpers}, may be empty
     */
    private Helper[] getHelpersArray() {
        return getHelpersArray(null);
    }

    /**
     * Generates an array of {@linkplain Helper helpers} from the tree map for any
     * {@linkplain Helper helpers} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Helper helpers}
     * in the tree map
     * 
     * @return  The array of {@link Helper hepers}, may be empty
     */
    private Helper[] getHelpersArray(String containsText) { // if containsText == null, no filter
        ArrayList<Helper> helperArrayList = new ArrayList<>();

        for (Helper helper : helpers.values()) {
            if (containsText == null || helper.getName().contains(containsText)) {
                helperArrayList.add(helper);
            }
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
    private boolean save() throws IOException {
        Helper[] helperArray = getHelpersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),helperArray);
        return true;
    }

    /**
     * Loads {@linkplain Helper helper} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        helpers = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of helpers
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Helper[] helperArray = objectMapper.readValue(new File(filename),Helper[].class);

        // Add each helper to the tree map and keep track of the greatest id
        for (Helper helper : helperArray) {
            helpers.put(helper.getId(),helper);
            if (helper.getId() > nextId)
                nextId = helper.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
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
    public Helper[] findHelpers(String containsText) {
        synchronized(helpers) {
            return getHelpersArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Helper getHelper(int id) {
        synchronized(helpers) {
            if (helpers.containsKey(id))
                return helpers.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Helper createHelper(Helper helper) throws IOException {
        synchronized(helpers) {
            // We create a new helper object because the id field is immutable
            // and we need to assign the next unique id

            Helper newHelper = new Helper(nextId(),helper.getName(), helper.getCart(),helper.getPassword(),helper.isAdmin());

            helpers.put(newHelper.getId(),newHelper);
            save(); // may throw an IOException
            return newHelper;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Helper updateHelpers(Helper helper) throws IOException {
        synchronized(helpers) {
            if (helpers.containsKey(helper.getId()) == false)
                return null;  // helper does not exist

            helpers.put(helper.getId(),helper);
            save(); // may throw an IOException
            return helper;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteHelper(int id) throws IOException {
        synchronized(helpers) {
            if (helpers.containsKey(id)) {
                helpers.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
