package com.example.garage.model.apimodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatusModel extends ResultModel {
    private List<TicketStatusModel> status;

}
