package com.Wipro.Student.service;

import com.Wipro.Student.model.Address;
import com.Wipro.Student.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    public void saveAddress(Address address) {
        addressRepository.save(address);
    }
}
