package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IReceiptLineDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.ReceiptLine;
import com.sandrajavaschool.OnlineStore.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptLineService implements IReceiptLineService {

    @Autowired
    private IReceiptLineDao receiptLineDao;

    @Override
    @Transactional
    public List<ReceiptLine> findAll() {
        return receiptLineDao.findAll();
    }

    @Override
    @Transactional
    public void save(ReceiptLine receiptLine) {
        receiptLineDao.save(receiptLine);
    }

    @Override
    @Transactional
    public ReceiptLine findOne(Long id) {
        return receiptLineDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        receiptLineDao.deleteById(id);
    }
}
