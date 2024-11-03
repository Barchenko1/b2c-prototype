package com.b2c.prototype.modal.embedded.history;

import com.b2c.prototype.modal.embedded.user.TempUserProfile;
import com.b2c.prototype.modal.embedded.item.TempItemData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "view_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "temp_user_profile_id")
    private TempUserProfile tempUserProfile;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "view_history_temp_item_data",
            joinColumns = {@JoinColumn(name = "view_history_id")},
            inverseJoinColumns = {@JoinColumn(name = "temp_item_data_id")}
    )
    private Set<TempItemData> itemDataSet = new HashSet<>();
}
