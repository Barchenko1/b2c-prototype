package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.entity.tenant.Tenant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "store_general_board")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "StoreGeneralBoard.findByArticularUniqIds",
                query = "SELECT DISTINCT sgb FROM StoreGeneralBoard sgb " +
                        "LEFT JOIN sgb.articularStocks st " +
                        "LEFT JOIN st.availabilityState sts " +
                        "LEFT JOIN st.articularItemQuantity saiq " +
                        "LEFT JOIN saiq.articularItem ai " +
                        "WHERE ai.articularUniqId IN :articularIds"
        )
})
public class StoreGeneralBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "store_general_board_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ArticularStock> articularStocks = new HashSet<>();
}
