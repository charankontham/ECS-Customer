package com.ecs.ecs_customer.validations;

import com.ecs.ecs_customer.dto.AddressDto;

import java.util.regex.Pattern;

public class AddressValidation {
    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");

    public static boolean validateAddress(AddressDto addressDto) {
        String[] userIdArray = addressDto.getUserId().split("_");
        return BasicValidation.stringValidation(addressDto.getStreet())
                && BasicValidation.stringValidation(addressDto.getName())
                && BasicValidation.stringValidation(addressDto.getContact())
                && BasicValidation.stringValidation(addressDto.getCity())
                && BasicValidation.stringValidation(addressDto.getState())
                && BasicValidation.stringValidation(addressDto.getZip())
                && BasicValidation.stringValidation(addressDto.getCountry())
                && BasicValidation.stringValidation(addressDto.getUserId())
                && userIdArray.length == 2 ;
    }

    public static boolean isIntegerRegex(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return INTEGER_PATTERN.matcher(str).matches();
    }
}
