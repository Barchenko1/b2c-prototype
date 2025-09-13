package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.entity.address.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Store.findStoreByStoreId",
                query = "SELECT s FROM Store s " +
                        "WHERE s.storeId = :storeId"
        ),
        @NamedQuery(
                name = "Store.findStoreWithAddressByStoreId",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "WHERE s.storeId = :storeId"
        ),
        @NamedQuery(
                name = "Store.findStoreWithAddressArticularItemQuantityByStoreId",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.articularStocks sas " +
                        "LEFT JOIN FETCH sas.articularItemQuantities aiq " +
                        "LEFT JOIN FETCH aiq.articularItem ai " +
                        "WHERE s.storeId = :storeId"
        ),
        @NamedQuery(
                name = "Store.findStoreWithAddressArticularItemQuantityByCountry",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.articularStocks sas " +
                        "LEFT JOIN FETCH sas.articularItemQuantities aiq " +
                        "LEFT JOIN FETCH aiq.articularItem ai " +
                        "WHERE c.value = :value"
        ),
        @NamedQuery(
                name = "Store.findStoreWithAddressArticularItemQuantityByCountryCity",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.articularStocks sas " +
                        "LEFT JOIN FETCH sas.articularItemQuantities aiq " +
                        "LEFT JOIN FETCH aiq.articularItem ai " +
                        "WHERE c.value = :value " +
                        "AND a.city =: value"
        ),
        @NamedQuery(
                name = "Store.findStoreWithAddressArticularItemQuantityByArticularId",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.articularStocks sas " +
                        "LEFT JOIN FETCH sas.articularItemQuantities aiq " +
                        "LEFT JOIN FETCH aiq.articularItem ai " +
                        "WHERE ai.articularId = : articularId"
        )
})
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "store_id", unique = true, nullable = false)
    private String storeId;
    private String storeName;
    private boolean isActive;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "store_id")
    @Builder.Default
    private List<ArticularStock> articularStocks = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
