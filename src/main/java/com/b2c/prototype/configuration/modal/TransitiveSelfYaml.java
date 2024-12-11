package com.b2c.prototype.configuration.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransitiveSelfYaml {
    private String name;
    private List<TransitiveSelfYaml> sub;
}
