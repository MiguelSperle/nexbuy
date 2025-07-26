package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.web.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.DeleteAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IDeleteAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IUpdateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IGetAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressesUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateAddressUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IGetAddressesUseCase;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.CreateAddressRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.requests.UpdateAddressRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses.GetAddressResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.web.dtos.responses.GetAddressesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final ICreateAddressUseCase createAddressUseCase;
    private final IUpdateAddressUseCase updateAddressUseCase;
    private final IGetAddressesUseCase getAddressesUseCase;
    private final IGetAddressUseCase getAddressUseCase;
    private final IDeleteAddressUseCase deleteAddressUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> createAddress(@RequestBody @Valid CreateAddressRequest createAddressRequest) {
        this.createAddressUseCase.execute(CreateAddressUseCaseInput.with(
                createAddressRequest.addressLine(),
                createAddressRequest.addressNumber(),
                createAddressRequest.zipCode(),
                createAddressRequest.neighborhood(),
                createAddressRequest.city(),
                createAddressRequest.uf(),
                createAddressRequest.complement()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Address created successfully"));
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<MessageResponse> updateAddress(
            @PathVariable String addressId,
            @RequestBody @Valid UpdateAddressRequest updateAddressRequest
    ) {
        this.updateAddressUseCase.execute(UpdateAddressUseCaseInput.with(
                addressId,
                updateAddressRequest.addressLine(),
                updateAddressRequest.addressNumber(),
                updateAddressRequest.zipCode(),
                updateAddressRequest.neighborhood(),
                updateAddressRequest.city(),
                updateAddressRequest.uf(),
                updateAddressRequest.complement()
        ));

        return ResponseEntity.ok().body(MessageResponse.from("Address updated successfully"));
    }

    @GetMapping
    public ResponseEntity<List<GetAddressesResponse>> getAddresses() {
        final GetAddressesUseCaseOutput getAddressesUseCaseOutput = this.getAddressesUseCase.execute();

        return ResponseEntity.ok().body(GetAddressesResponse.from(getAddressesUseCaseOutput));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<GetAddressResponse> getAddress(@PathVariable String addressId) {
        final GetAddressUseCaseOutput getAddressUseCaseOutput = this.getAddressUseCase.execute(GetAddressUseCaseInput.with(addressId));

        return ResponseEntity.ok().body(GetAddressResponse.from(getAddressUseCaseOutput));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<MessageResponse> deleteAddress(@PathVariable String addressId) {
        this.deleteAddressUseCase.execute(DeleteAddressUseCaseInput.with(addressId));

        return ResponseEntity.ok().body(MessageResponse.from("Address deleted successfully"));
    }
}
