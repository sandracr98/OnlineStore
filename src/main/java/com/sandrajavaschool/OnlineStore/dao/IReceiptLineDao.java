package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.ReceiptLine;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface defining data access operations for the ReceiptLine entity.
 */
public interface IReceiptLineDao extends JpaRepository<ReceiptLine,Long> {

}
