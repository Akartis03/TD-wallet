package com.wallet.tdwallet.model;

import lombok.*;
import java.sql.Timestamp;;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TransferHistory {
        private int id;
        private int debtorTransactionId;
        private int creditorTransactionId;
        private Timestamp transferDate;
}
