package com.example.garage.model.apimodel;

import com.example.garage.model.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParkModel extends ResultModel {
    private Ticket ticket;

}
