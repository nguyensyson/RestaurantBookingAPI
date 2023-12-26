package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.DinnerTableDTO;
import com.poly.bookingapi.dto.DinnerTableRequest;
import com.poly.bookingapi.entity.DinnerTable;
import com.poly.bookingapi.proxydto.DinnerTableProxy;
import com.poly.bookingapi.service.DinnerTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DinnerTableController {

    @Autowired
    private DinnerTableService dinnerTableService;

    @GetMapping("api/view/dinner-table/get-all")
    public ResponseEntity<Page<DinnerTableDTO>> getAllDinnerTable(@RequestBody DinnerTableRequest request) {
        return ResponseEntity.ok(dinnerTableService.getAll(request));
    }

    @PostMapping("api/admin/dinner-table/add")
    public ResponseEntity<DinnerTable> addDinnerTable(@RequestBody DinnerTableDTO dinnerTableDTO) {
        return ResponseEntity.ok(dinnerTableService.add(dinnerTableDTO));
    }

    @PutMapping("api/admin/dinner-table/update/{id}")
    public ResponseEntity<DinnerTable> updateDinnerTable(@RequestBody DinnerTableDTO dinnerTableDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(dinnerTableService.update(dinnerTableDTO, id));
    }

    @DeleteMapping("api/admin/dinner-table/delete/{id}")
    public ResponseEntity<DinnerTable> deleteDinnerTable(@PathVariable Integer id) {
        return ResponseEntity.ok(dinnerTableService.delete(id));
    }

    @GetMapping("api/view/dinner-table/findByDinningRoomId/{id}/{idRoom}")
    public List<DinnerTableProxy> findByDinningRoomId(@PathVariable Integer id,
                                                      @PathVariable Integer idRoom) {
        return dinnerTableService.getAllByDiningRoomId(id, idRoom);
    }
}
