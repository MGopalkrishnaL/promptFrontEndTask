package com.Wipro.Student.service;

import com.Wipro.Student.DTO.RequestData;
import com.Wipro.Student.model.Address;
import com.Wipro.Student.model.Student;
import com.Wipro.Student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final AddressService addressService;
    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> studentList = studentRepository.findAll();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    public ResponseEntity<String> saveStudent(RequestData requestData) {
        Student student = new Student();
        Address address = new Address();
        student.setFirstName(requestData.getFirstName());
        student.setLastName(requestData.getLastName());
        student.setPhoneNumber(requestData.getPhoneNumber());
        address.setCity(requestData.getCity());
        address.setColony(requestData.getColony());
        address.setCountry(requestData.getCountry());
        address.setDoorNumber(requestData.getDoorNumber());
        address.setPinCode(requestData.getPinCode());
        addressService.saveAddress(address);
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        student.setAddress(addressList);
        studentRepository.save(student);
        return new ResponseEntity<>("student saved successfully",HttpStatus.CREATED);
    }
}
