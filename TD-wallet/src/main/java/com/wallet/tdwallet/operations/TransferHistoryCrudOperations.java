package com.wallet.tdwallet.operations;

import com.wallet.tdwallet.connection.DBConnection;
import com.wallet.tdwallet.model.Account;
import com.wallet.tdwallet.model.TransferHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferHistoryCrudOperations implements CrudOperations<TransferHistory> {
    private DBConnection connectionDB;
    private final String transferHistoryIdCol = "transfer_history_id";
    private final String debtorTransactionIdCol = "debtor_transaction_id";
    private final String creditorTransactionIdCol = "creditor_transaction_id";
    private final String transferDateCol = "transfer_date";

    public TransferHistoryCrudOperations() {
        this.connectionDB = new DBConnection();
    }
    @Override
    public List<TransferHistory> findAll() {
        List<TransferHistory> transferHistories = new ArrayList<>();

        try (
                Connection connection = connectionDB.getConnection();
                PreparedStatement statement= connection.prepareStatement("SELECT * FROM transfer_history");
                ResultSet resultSet = statement.executeQuery()
        ){
            while (resultSet.next()){
                TransferHistory transferHistory = new TransferHistory();
                transferHistory.setId(resultSet.getInt(transferHistoryIdCol));
                transferHistory.setDebtorTransactionId(resultSet.getInt(debtorTransactionIdCol));
                transferHistory.setCreditorTransactionId(resultSet.getInt(creditorTransactionIdCol));
                transferHistory.setTransferDate(resultSet.getTimestamp(transferDateCol));

                transferHistories.add(transferHistory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transferHistories;
    }

    @Override
    public List<TransferHistory> saveAll(List<TransferHistory> toSave) {
        return null;
    }

    @Override
    public TransferHistory save(TransferHistory transferHistory) {
        try (
                Connection connection = connectionDB.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(

                        "INSERT INTO transfer_history (transfer_history_id, debtor_transaction_id ,creditor_transaction_id, transfer_date) VALUES (?, ?, ?, ?)"

                )) {
            insertStatement.setInt(1,transferHistory.getId());
            insertStatement.setInt(2,transferHistory.getDebtorTransactionId());
            insertStatement.setInt(3,transferHistory.getCreditorTransactionId());
            insertStatement.setTimestamp(4, transferHistory.getTransferDate());

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transferHistory;
    }
}
