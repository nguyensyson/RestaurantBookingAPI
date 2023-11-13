package com.poly.bookingapi.controller;

import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.request.ClientRequest;
import com.poly.bookingapi.response.ClientResponse;
import com.poly.bookingapi.service.ManageClientService;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/client")
public class ManageClientController {
    @Autowired
    private ManageClientService manageClientService;

    @GetMapping("/search")
    public ResponseEntity<List<Client>> getName(@RequestParam String username) {
        return ResponseEntity.ok(manageClientService.searchByName(username));
    }

//    @GetMapping("/pages")
//    public List<ClientRequest> getClientPages(
//            @RequestParam(defaultValue = "0") int page,
//            @Request Param(defaultValue = "5") int size) {
//        Pageable page1 = PageRequest.of(page, size);
//        return this.manageClientService.findAll(page1);
//    }


    @GetMapping("/pages")
    public List<ClientRequest> getClientPages(
            @RequestParam Optional<Integer> page) {
        return this.manageClientService.findAll(page.orElse(0));
    }

    @GetMapping("search/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Integer id) {
        Optional<Client> client = this.manageClientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khách hàng với ID: " + id);
        }
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}/reservations")
    public List<Reservation> getReservationsByClient(@PathVariable Integer id) {
        Optional<Client> clientResponse = this.manageClientService.getClientById(id);
        if (clientResponse == null) {
            return null;
        }
        return manageClientService.getReservationsByClient(clientResponse);

    }
}

