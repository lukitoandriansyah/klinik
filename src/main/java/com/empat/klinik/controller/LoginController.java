package com.empat.klinik.controller;

import com.empat.klinik.Repository.KaryawanRepository;
import com.empat.klinik.model.Dto.DefaultResponse;
import com.empat.klinik.model.Dto.LoginDto;
import com.empat.klinik.model.Entity.Karyawan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/mainhome")
public class LoginController {

    @Autowired
    private KaryawanRepository karyawanRepository;

    @PostMapping("/login")
    public DefaultResponse login(@RequestBody LoginDto loginDto){
        DefaultResponse response = new DefaultResponse();
        Optional<Karyawan> optionalKaryawanUsername = karyawanRepository.findByUsername(loginDto.getUsername());
        Optional<Karyawan> optionalKaryawanPassword = karyawanRepository.findByPassword(loginDto.getPassword());

        if(optionalKaryawanUsername.isPresent()){
            if(!optionalKaryawanPassword.isPresent()){
                response.setStatus(Boolean.FALSE);
                response.setPesan("Password Salah!");
            } else if (optionalKaryawanPassword.isPresent()) {
                response.setStatus(Boolean.TRUE);
                response.setPesan("Berhasil Login");
            }

        }else if(optionalKaryawanPassword.isPresent()) {
            if (!optionalKaryawanUsername.isPresent()) {
                response.setStatus(Boolean.FALSE);
                response.setPesan("Username Salah!");
            } else if (optionalKaryawanUsername.isPresent()) {
                response.setStatus(Boolean.TRUE);
                response.setPesan("Berhasil Login");
            }
        }else{
            response.setStatus(Boolean.FALSE);
            response.setPesan("Username dan Password Salah!");
        }
        return response;
    }

    /*Cek karyawan berdasarkan NIK*/
    @GetMapping("/byid/{nik}")
    public DefaultResponse getByIdKaryawan(@PathVariable String nik){
        DefaultResponse df = new DefaultResponse();
        Optional<Karyawan> namaKaryawan = karyawanRepository.findById(nik);
        if(namaKaryawan.isPresent()){
            df.setStatus(Boolean.TRUE);
            df.setPesan("Data Ditemukan");
        }else{
            df.setStatus(Boolean.FALSE);
            df.setPesan("Data Tidak Ada");
        }

        return df;
    }

}
