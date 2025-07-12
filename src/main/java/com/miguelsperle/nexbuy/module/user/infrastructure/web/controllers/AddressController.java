package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.DeleteAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.UpdateAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAddressUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAddressesUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.CreateAddressRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.UpdateAddressRequest;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.GetAddressResponse;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.GetAddressesResponse;
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
        this.createAddressUseCase.execute(new CreateAddressUseCaseInput(
                createAddressRequest.addressLine(),
                createAddressRequest.addressNumber(),
                createAddressRequest.zipCode(),
                createAddressRequest.neighborhood(),
                createAddressRequest.city(),
                createAddressRequest.uf(),
                createAddressRequest.complement()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Address created successfully"));
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<MessageResponse> updateAddress(
            @PathVariable String addressId,
            @RequestBody @Valid UpdateAddressRequest updateAddressRequest
    ) {
        this.updateAddressUseCase.execute(new UpdateAddressUseCaseInput(
                addressId,
                updateAddressRequest.addressLine(),
                updateAddressRequest.addressNumber(),
                updateAddressRequest.zipCode(),
                updateAddressRequest.neighborhood(),
                updateAddressRequest.city(),
                updateAddressRequest.uf(),
                updateAddressRequest.complement()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Address updated successfully"));
    }

    @GetMapping
    public ResponseEntity<List<GetAddressesResponse>> getAddresses() {
        final GetAddressesUseCaseOutput getAddressesUseCaseOutput = this.getAddressesUseCase.execute();

        return ResponseEntity.ok().body(GetAddressesResponse.fromOutput(getAddressesUseCaseOutput));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<GetAddressResponse> getAddress(@PathVariable String addressId) {
        final GetAddressUseCaseOutput getAddressUseCaseOutput = this.getAddressUseCase.execute(new GetAddressUseCaseInput(addressId));

        return ResponseEntity.ok().body(GetAddressResponse.fromOutput(getAddressUseCaseOutput));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<MessageResponse> deleteAddress(@PathVariable String addressId) {
        this.deleteAddressUseCase.execute(new DeleteAddressUseCaseInput(addressId));

        return ResponseEntity.ok().body(new MessageResponse("Address deleted successfully"));
    }
}
