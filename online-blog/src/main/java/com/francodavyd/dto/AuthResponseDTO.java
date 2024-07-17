package com.francodavyd.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;

@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponseDTO (String username, String message, String jwt, boolean status){

}
