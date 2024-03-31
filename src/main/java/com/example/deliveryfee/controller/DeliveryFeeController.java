package com.example.deliveryfee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.deliveryfee.service.FeeCalculationService;

@RequestMapping("/fee")
@RestController
public class DeliveryFeeController {

    private static final int TALLINN_WMOCODE = 26038;
    private static final int TARTU_WMOCODE = 41025;
    private static final int PARNU_WMOCODE = 41803;

    private final FeeCalculationService feeCalculationService;

    public DeliveryFeeController(FeeCalculationService feeCalculationService) {
        this.feeCalculationService = feeCalculationService;
    }

    @GetMapping("")
    @Operation(summary = "Calculate delivery fee",
            description = "Calculates the delivery fee based on the provided location and vehicle type.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully calculated the delivery fee",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters provided"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<String> getFee(@RequestParam String location, @RequestParam String vehicleType) {

        if (location == null || vehicleType == null || location.isEmpty() || vehicleType.isEmpty()) {
            return ResponseEntity.badRequest().body("Location and vehicleType must be provided");
        }
        location = location.replaceAll("\\s+", "");
        vehicleType = vehicleType.replaceAll("\\s+", "");

        if (!location.equalsIgnoreCase("tallinn") && !location.equalsIgnoreCase("tartu")
                && !location.equalsIgnoreCase("pärnu")) {
            return ResponseEntity.badRequest().body("Location must be a Tallinn, Tartu or Pärnu");
        } else if (!vehicleType.equalsIgnoreCase("car") && !vehicleType.equalsIgnoreCase("bike")
                && !vehicleType.equalsIgnoreCase("scooter")) {
            return ResponseEntity.badRequest().body("Vehicle must be a car, bike or scooter");
        } else {

            try {
                String fee = feeCalculationService.calculateFeeBasedOnWeather(mapLocationToWmoCode(location), vehicleType);
                return ResponseEntity.ok(fee);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
            }
        }

    }


    private int mapLocationToWmoCode(String location) {
        return switch (location.toLowerCase()) {
            case "tallinn" -> TALLINN_WMOCODE;
            case "tartu" -> TARTU_WMOCODE;
            case "pärnu" -> PARNU_WMOCODE;
            default -> 0;
        };
    }

}