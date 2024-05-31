package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Helper;

public interface HelperDAO {
    /**
     * Retrieves a {@linkplain Helper helper} with the given name
     * 
     * @param name The name of the {@link Helper helper} to get
     * 
     * @return a {@link Helper helper} object with the matching name
     * <br>
     * null if no {@link Helper helper} with a matching name is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Helper getHelper(String name) throws IOException;

    /**
     * Retrieves all {@linkplain Helper helpers}
     * @return An array of {@link Helper helper} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    
    Helper[] getHelpers() throws IOException;


    /**
     * Finds all {@linkplain Helper helpers} whose name contains the given text
     * 
     * @param containText The text to match against
     *
     * @return An array of {@link Helper helpers} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Helper[] findHelpers(String containText) throws IOException;
    
    /**
     * Creates and saves a {@linkplain Helper helper}
     * 
     * @param name {@linkplain String name} object to be created and saved
     * <br>
     * The name of the need object is ignored and a new unique need is assigned
     * 
     * @return new {@link Helper helper} if successful, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    Helper createHelper(String name) throws IOException;

    /**
     * Updates and saves a {@linkplain Helper helper}
     * 
     * @param name
     * 
     * @param helper {@link Helper helper} object to be updated and saved
     * 
     * @return updated {@link Helper helper} if successful, null if
     * {@link Helper helper} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Helper updateHelper(String name, Helper helper) throws IOException;

    /**
     * Deletes a {@linkplain Helper helper} with the given name

     * @param name The name of the {@link Helper helper}
     * 
     * @return true if the {@link Helper helper} was deleted
     * <br>
     * false if need with the given id does not exist
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteHelper(String name) throws IOException;
}
