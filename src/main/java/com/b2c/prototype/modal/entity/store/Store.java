package com.b2c.prototype.modal.entity.store;

import com.b2c.prototype.modal.entity.address.Address;
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
import jakarta.persistence.OneToOne;
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
@Table(name = "store")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Store.findStoreByRegionStoreId",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.tenant r " +
                        "WHERE r.code = : code and s.storeUniqId = :storeUniqId"
        ),
        @NamedQuery(
                name = "Store.findStoreByRegionStoreIdDetails",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.tenant r " +
                        "LEFT JOIN FETCH s.articularStocks sa " +

                        "LEFT JOIN FETCH sa.articularItemQuantity sasq " +
                        "LEFT JOIN FETCH sasq.articularItem sasqa " +
                        "LEFT JOIN FETCH sasqa.articularGroup sasqaa " +
                        "LEFT JOIN FETCH sa.availabilityState sas " +
                        "WHERE r.code = : code and s.storeUniqId = :storeUniqId"
        ),
        @NamedQuery(
                name = "Store.findAllStoreByRegion",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.tenant r " +
                        "LEFT JOIN FETCH s.articularStocks sa " +
                        "WHERE r.code = : code"
        ),
        @NamedQuery(
                name = "Store.findAllStoreByRegionAndCountry",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.tenant r " +
                        "LEFT JOIN FETCH s.articularStocks sa " +
                        "WHERE r.code = : code and c.key = : key"
        ),
        @NamedQuery(
                name = "Store.findAllStoreByRegionAndCountryAndCity",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.tenant r " +
                        "LEFT JOIN FETCH s.articularStocks sa " +
                        "WHERE r.code = : code and c.key = : key and a.city = : city "
        ),

        @NamedQuery(
                name = "Store.findAllStoresByRegionAndArticularIds",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.tenant t " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.articularStocks sas " +
                        "LEFT JOIN FETCH sas.articularItemQuantity aiq " +
                        "LEFT JOIN FETCH aiq.articularItem ai " +
                        "WHERE t.code = :code and ai.articularUniqId IN :articularIds"
        ),

        /// ///
        @NamedQuery(
                name = "Store.findStoreWithAddressByStoreId",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "WHERE s.storeUniqId = :storeUniqId"
        ),
        @NamedQuery(
                name = "Store.findStoreWithAddressArticularItemQuantityByStoreId",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.articularStocks sas " +
                        "LEFT JOIN FETCH sas.articularItemQuantity aiq " +
                        "LEFT JOIN FETCH aiq.articularItem ai " +
                        "WHERE s.storeUniqId = :storeUniqId"
        ),
        @NamedQuery(
                name = "Store.findStoreWithAddressArticularItemQuantityByCountry",
                query = "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.address a " +
                        "LEFT JOIN FETCH a.country c " +
                        "LEFT JOIN FETCH s.articularStocks sas " +
                        "LEFT JOIN FETCH sas.articularItemQuantity aiq " +
                        "LEFT JOIN FETCH aiq.articularItem ai " +
                        "WHERE c.key = :key"
        )
})
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "store_uniq_id", unique = true, nullable = false)
    private String storeUniqId;
    private String storeName;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "store_id")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ArticularStock> articularStocks = new HashSet<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
