package com.b2c.prototype.modal.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "device")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = "Device.getLatest",
        query = "SELECT d FROM Device d ORDER BY d.lastSignedIn DESC"
)
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String ipAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSignedIn;
    private boolean isThisDevice;
}
