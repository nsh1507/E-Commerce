package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Basket;


@Component
public class BasketFileDAO implements BasketDAO {
    private static final Logger LOG = Logger.getLogger(BasketFileDAO.class.getName());
    Map<Integer,Basket> basketes;   // Provides a local cache of the product objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between basketes
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new basket
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Basket File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public BasketFileDAO(@Value("${basketes.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the products from the file
    }

    /**
     * Generates the next id for a new {@linkplain Basket basket}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Basket basket} from the tree map
     * 
     * @return  The array of {@link Basket basket}, may be empty
     */
    private Basket[] getBasketesArray() {
        return getBasketesArray(null);
    }

    /**
     * Generates an array of {@linkplain Basket basket} from the tree map for any
     * {@linkplain Basket basket} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Basket Basket}
     * in the tree map
     * 
     * @return  The array of {@link Basket basket}, may be empty
     */
    private Basket[] getBasketesArray(String containsText) { // if containsText == null, no filter
        ArrayList<Basket> basketArrayList = new ArrayList<>();

        for (Basket basket : basketes.values()) {
            if (containsText == null || basket.getName().contains(containsText)) {
                basketArrayList.add(basket);
            }
        }

        Basket[] basketArray = new Basket[basketArrayList.size()];
        basketArrayList.toArray(basketArray);
        return basketArray;
    }

    /**
     * Saves the {@linkplain Basket basketes} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Basket basket} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Basket[] basketArray = getBasketesArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),basketArray);
        return true;
    }

    /**
     * Loads {@linkplain Basket basketes} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
      //  ArrayList<Product> testPool = new ArrayList<Product>();
      //  testPool.add(new Product(4, "testproduct", 23, 45));
       // Basket testBasket = new Basket(5, "testadd", testPool);
       // createBasket(testBasket);
        //save();
       //above is the creation of a test basket object and adding it to json. dont question why its here. 
        basketes = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Basket[] basketArray = objectMapper.readValue(new File(filename),Basket[].class);

        // Add each product to the tree map and keep track of the greatest id
        for (Basket basket : basketArray) {
            basketes.put(basket.getId(),basket);
            if (basket.getId() > nextId)
                nextId = basket.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Basket[] getBasketes() {
        synchronized(basketes) {
            return getBasketesArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Basket[] findBasketes(String containsText) {
        synchronized(basketes) {
            return getBasketesArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Basket getBasket(int id) {
        synchronized(basketes) {
            if (basketes.containsKey(id))
                return basketes.get(id);
            else
                return null;
        }
    }


    /**
    ** {@inheritDoc}
     */
    @Override
    public Basket createBasket(Basket basket) throws IOException {
        synchronized(basketes) {
            // We create a new basket object because the id field is immutable
            // and we need to assign the next unique id
            System.out.println(basket.getName());
            Basket newBasket = new Basket(nextId(),basket.getName(), basket.getPrice());
            basketes.put(newBasket.getId(),newBasket);
            save(); // may throw an IOException
            return newBasket;
        }
    }

     /**
    ** {@inheritDoc}
     */
    @Override
    public Basket updateBasket(Basket basket) throws IOException {
        synchronized(basketes) {
            if (basketes.containsKey(basket.getId()) == false)
                return null;  // basket does not exist

                basketes.put(basket.getId(),basket);
            save(); // may throw an IOException
            return basket;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteBasket(int id) throws IOException {
        synchronized(basketes) {
            if (basketes.containsKey(id)) {
                basketes.remove(id);
                return save();
            }
            else
                return false;
        }
    }
    

}
