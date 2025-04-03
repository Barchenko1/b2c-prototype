package com.b2c.prototype.controller.store;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.processor.store.IStoreProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {

    private final IStoreProcess storeProcess;

    public StoreController(IStoreProcess storeProcess) {
        this.storeProcess = storeProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStore(@RequestBody final StoreDto storeDto) {
        storeProcess.saveStore(storeDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putItemData(@RequestParam final Map<String, String> requestParams,
                                            @RequestBody final StoreDto storeDto) {
        storeProcess.updateStore(requestParams, storeDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteStore(@RequestParam final Map<String, String> requestParams) {
        storeProcess.deleteStore(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseStoreDto> getAllResponseStores(@RequestParam final Map<String, String> requestParams) {
        return storeProcess.getAllResponseStores(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseStoreDto> getAllResponseStoresByArticularId(@RequestParam final Map<String, String> requestParams) {
        return storeProcess.getAllResponseStoresByArticularId(requestParams);
    }

    @GetMapping(value = "/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseStoreDto> getStore(@RequestParam final Map<String, String> requestParams,
                                                     @PathVariable final String storeId) {
        return new ResponseEntity<>(storeProcess.getStore(requestParams, storeId), HttpStatus.OK);
    }

}
