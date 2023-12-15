package com.wallet.tdwallet.operations;

import com.wallet.tdwallet.connection.DBConnection;
import com.wallet.tdwallet.model.TransactionCategory;
import java.sql.*;
import java.util.*;

public class TransactionCategoryCrudOperations implements CrudOperations<TransactionCategory> {
    private DBConnection connectionDB;

    public TransactionCategoryCrudOperations() {
        this.connectionDB = new DBConnection();
    }
    private final String transactionCategoryIdCol = "transaction_category_id";
    private final String transactionCategoryNameCol = "name";
    private final String transactionCategoryTypeCol = "type";



    @Override
    public List<TransactionCategory> findAll() {
        List<TransactionCategory> transactionsCategories = new ArrayList<>();

        try (
                Connection connection = connectionDB.getConnection();
                PreparedStatement statement= connection.prepareStatement("SELECT * FROM transaction_categories");
                ResultSet resultSet = statement.executeQuery()
        ){
            while (resultSet.next()){
                TransactionCategory transactionCategory = new TransactionCategory(
                        resultSet.getInt(transactionCategoryIdCol),
                        resultSet.getString(transactionCategoryNameCol),
                        resultSet.getString(transactionCategoryTypeCol)
                );
                transactionsCategories.add(transactionCategory);
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionsCategories;
    }

    @Override
    public List<TransactionCategory> saveAll(List<TransactionCategory> toSave) {
        return null;
    }

    @Override
    public TransactionCategory save(TransactionCategory transactionCategory) {
        try (
                Connection connection = connectionDB.getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(
                        "SELECT * FROM transaction_categories WHERE transaction_category_id = ? AND name = ?"
                )
        ) {
            selectStatement.setInt(1, transactionCategory.getId());
            selectStatement.setString(2, transactionCategory.getName());

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("this transaction category already exist");
                    return transactionCategory;
                } else {
                    try (PreparedStatement insertStatement = connection.prepareStatement(
                            "INSERT INTO transaction_categories (transaction_category_id, name, type) VALUES (?, ?, ?)"
                    )) {
                        insertStatement.setInt(1, transactionCategory.getId());
                        insertStatement.setString(2, transactionCategory.getName());
                        insertStatement.setString(3,transactionCategory.getType());

                        insertStatement.executeUpdate();
                    }
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionCategory;
    }
}
