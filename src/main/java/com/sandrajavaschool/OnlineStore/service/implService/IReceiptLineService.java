package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.ReceiptLine;
import com.sandrajavaschool.OnlineStore.entities.User;

import java.util.List;

/**
 * Service interface for managing receipt lines.
 */
public interface IReceiptLineService {

    /**
     * Retrieves all receipt lines.
     *
     * @return a list of all receipt lines
     */
    List<ReceiptLine> findAll();

    /**
     * Saves a receipt line.
     *
     * @param receiptLine the receipt line to be saved
     */
    void save(ReceiptLine receiptLine);

    /**
     * Retrieves a receipt line by its ID.
     *
     * @param id the ID of the receipt line
     * @return the receipt line with the specified ID, or null if not found
     */
    ReceiptLine findOne(Long id);

    /**
     * Deletes a receipt line by its ID.
     *
     * @param id the ID of the receipt line to be deleted
     */
    void delete(Long id);

}
