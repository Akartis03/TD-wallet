package com.wallet.tdwallet.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TransactionCategory {
        private int id;
        private String name;
        private String type;
}
