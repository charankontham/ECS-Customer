package com.ecs.ecs_customer.validations;

import com.ecs.ecs_customer.dto.AddressDto;

public class AddressValidation {
    public static boolean validateAddress(AddressDto addressDto) {
        return BasicValidation.stringValidation(addressDto.getStreet())
                && BasicValidation.stringValidation(addressDto.getName())
                && BasicValidation.stringValidation(addressDto.getContact())
                && BasicValidation.stringValidation(addressDto.getCity())
                && BasicValidation.stringValidation(addressDto.getState())
                && BasicValidation.stringValidation(addressDto.getZip())
                && BasicValidation.stringValidation(addressDto.getCountry());
    }
}
