package com.shirtstore.service;

import com.shirtstore.api.NominatimAPI;
import com.shirtstore.api.OSRMAPI;

public class DistanceService {
    public double calculateDistance(String startAddress, String endAddress) throws Exception {
        double[] startCoordinates = NominatimAPI.getCoordinatesFromAddress(startAddress);
        double[] endCoordinates = NominatimAPI.getCoordinatesFromAddress(endAddress);

        return OSRMAPI.getDistance(startCoordinates, endCoordinates);
    }
}
