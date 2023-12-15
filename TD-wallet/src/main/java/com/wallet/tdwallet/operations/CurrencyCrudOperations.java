package com.wallet.tdwallet.operations;

import com.wallet.tdwallet.connection.DBConnection;
import com.wallet.tdwallet.model.Currency;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyCrudOperations implements CrudOperations<Currency>{
    DBConnection dbConnection = new DBConnection();
    @Override
    public List<Currency> findAll() {
        List<Currency> currencyList = new ArrayList<>();
        try (Statement statement = dbConnection.getConnection().createStatement()) {
            String query = "SELECT * from currency ";
            try (ResultSet result = statement.executeQuery(query)) {
                while (result.next()) {
                    Currency currency = new Currency();
                    currency.setCurrencyId(result.getInt("currency_id"));
                    currency.setCurrencyName(result.getString("currency_name"));
                    currency.setCurrencyCode(result.getString("currency_code"));

                    currencyList.add(currency);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching transaction from the database", e);
        }
        return currencyList;
    }

    @Override
    public List<Currency> saveAll(List<Currency> toSave) {
        List<Currency> currencyList = new ArrayList<>();
        try {
            for (Currency currency : toSave) {
                String query = "INSERT INTO currency (currency_name,currency_code) VALUES ( ?, ?)";
                PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(query);
                preparedStatement.setString(1, currency.getCurrencyName());
                preparedStatement.setString(2, currency.getCurrencyCode());

                preparedStatement.executeUpdate();
                currencyList.add(currency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencyList;
    }

    @Override
    public Currency save(Currency toSave) {
        try {
            String query = "INSERT INTO currency (name, currency_code) VALUES ( ?, ?)";
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, toSave.getCurrencyName());
            preparedStatement.setString(2, toSave.getCurrencyCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toSave;
    }
}
