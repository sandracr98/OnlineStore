package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.entities.ReceiptLine;
import com.sandrajavaschool.OnlineStore.entities.User;

import java.util.List;

public interface IReceiptLineService {

    public List<ReceiptLine> findAll();
    public void save(ReceiptLine receiptLine);

    public ReceiptLine findOne(Long id);

    public void delete(Long id);

}
