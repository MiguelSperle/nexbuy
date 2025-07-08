package com.miguelsperle.nexbuy.module.user.infrastructure.web.controllers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.MessageResponse;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.*;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.complements.PersonComplementInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.*;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.domain.enums.PersonType;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.requests.*;
import com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final ICreateUserUseCase createUserUseCase;
    private final IAuthenticateUseCase authenticateUseCase;
    private final IResendVerificationCodeUseCase resendVerificationCodeUseCase;
    private final IUpdateUserToVerifiedUseCase updateUserToVerifiedUseCase;
    private final IRefreshTokenUseCase refreshTokenUseCase;
    private final ICreatePasswordResetCodeUseCase createPasswordResetCodeUseCase;
    private final IValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase;
    private final IResetUserPasswordUseCase resetUserPasswordUseCase;
    private final IUpdateUserUseCase updateUserUseCase;
    private final IUpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final IGetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final ICreateAddressUseCase createAddressUseCase;
    private final IUpdateAddressUseCase updateAddressUseCase;
    private final IGetAddressesUseCase getAddressesUseCase;
    private final IGetAddressUseCase getAddressUseCase;
    private final IDeleteAddressUseCase deleteAddressUseCase;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        PersonComplementInput personComplementInput = null;

        final PersonType convertedToPersonType = PersonType.valueOf(createUserRequest.personType());

        if (convertedToPersonType == PersonType.NATURAL_PERSON && createUserRequest.naturalPersonComplement() != null) {
            personComplementInput = new PersonComplementInput(
                    createUserRequest.naturalPersonComplement().cpf(),
                    createUserRequest.naturalPersonComplement().generalRegister(),
                    null, null, null, null
            );
        } else if (convertedToPersonType == PersonType.LEGAL_PERSON && createUserRequest.legalPersonComplement() != null) {
            personComplementInput = new PersonComplementInput(
                    null, null,
                    createUserRequest.legalPersonComplement().cnpj(),
                    createUserRequest.legalPersonComplement().fantasyName(),
                    createUserRequest.legalPersonComplement().legalName(),
                    createUserRequest.legalPersonComplement().stateRegistration()
            );
        }

        this.createUserUseCase.execute(new CreateUserUseCaseInput(
                createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.email(),
                createUserRequest.password(),
                createUserRequest.phoneNumber(),
                convertedToPersonType,
                personComplementInput
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User created successfully"));
    }

    @PostMapping("/authentication")
    public ResponseEntity<Object> authenticate(@RequestBody @Valid AuthenticateRequest authenticateRequest) {
        final AuthenticateUseCaseOutput authenticateUseCaseOutput = this.authenticateUseCase.execute(new AuthenticateUseCaseInput(
                authenticateRequest.email(), authenticateRequest.password()
        ));

        return ResponseEntity.ok().body(AuthenticateResponse.fromOutput(authenticateUseCaseOutput));
    }

    @PostMapping("/verification-code")
    public ResponseEntity<Object> resendVerificationCode(@RequestBody @Valid ResendVerificationCodeRequest resendVerificationCodeRequest) {
        this.resendVerificationCodeUseCase.execute(new ResendVerificationCodeUseCaseInput(
                resendVerificationCodeRequest.email()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Verification code sent successfully"));
    }

    @PatchMapping("/verification")
    public ResponseEntity<Object> updateUserToVerified(@RequestBody @Valid UpdateUserToVerifiedRequest updateUserToVerifiedRequest) {
        this.updateUserToVerifiedUseCase.execute(new UpdateUserToVerifiedUseCaseInput(
                updateUserToVerifiedRequest.code()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User verified successfully"));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<Object> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        final RefreshTokenUseCaseOutput refreshTokenUseCaseOutput = this.refreshTokenUseCase.execute(new RefreshTokenUseCaseInput(
                refreshTokenRequest.refreshToken()
        ));

        return ResponseEntity.ok().body(RefreshTokenResponse.fromOutput(refreshTokenUseCaseOutput));
    }

    @PostMapping("/password-recovery")
    public ResponseEntity<Object> createPasswordResetCode(@RequestBody @Valid CreatePasswordResetCodeRequest createPasswordResetCodeRequest) {
        this.createPasswordResetCodeUseCase.execute(new CreatePasswordResetCodeUseCaseInput(
                createPasswordResetCodeRequest.email()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Password reset code sent successfully"));
    }

    @PostMapping("/password-recovery/validation")
    public ResponseEntity<Object> validatePasswordResetCode(@RequestBody @Valid ValidatePasswordResetCodeRequest validatePasswordResetCodeRequest) {
        final ValidatePasswordResetCodeUseCaseOutput validatePasswordResetCodeUseCaseOutput = this.validatePasswordResetCodeUseCase.execute(new ValidatePasswordResetCodeUseCaseInput(
                validatePasswordResetCodeRequest.code()
        ));

        return ResponseEntity.ok().body(ValidatePasswordResetCodeResponse.fromOutput(validatePasswordResetCodeUseCaseOutput));
    }

    @PatchMapping("/password-reset")
    public ResponseEntity<Object> resetUserPassword(@RequestBody @Valid ResetUserPasswordRequest resetUserPasswordRequest) {
        this.resetUserPasswordUseCase.execute(new ResetUserPasswordUseCaseInput(
                resetUserPasswordRequest.code(), resetUserPasswordRequest.password()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User password reset successfully"));
    }

    @PatchMapping("/information")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        this.updateUserUseCase.execute(new UpdateUserUseCaseInput(
                updateUserRequest.firstName(), updateUserRequest.lastName(),
                updateUserRequest.phoneNumber()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User updated successfully"));
    }

    @PatchMapping("/password")
    public ResponseEntity<Object> updateUserPassword(@RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        this.updateUserPasswordUseCase.execute(new UpdateUserPasswordUseCaseInput(
                updateUserPasswordRequest.currentPassword(), updateUserPasswordRequest.password()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User password updated successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getAuthenticatedUser() {
        final GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput = this.getAuthenticatedUserUseCase.execute();

        if (getAuthenticatedUserUseCaseOutput.authenticatedUser().getPersonType() == PersonType.NATURAL_PERSON) {
            return ResponseEntity.ok().body(GetAuthenticatedUserNaturalPersonResponse.fromOutput(getAuthenticatedUserUseCaseOutput));
        } else {
            return ResponseEntity.ok().body(GetAuthenticatedUserLegalPersonResponse.fromOutput(getAuthenticatedUserUseCaseOutput));
        }
    }

    @PostMapping("/addresses")
    public ResponseEntity<Object> createAddress(@RequestBody @Valid CreateAddressRequest createAddressRequest) {
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

    @PatchMapping("/addresses/{addressId}")
    public ResponseEntity<Object> updateAddress(
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

    @GetMapping("/addresses")
    public ResponseEntity<List<GetAddressesResponse>> getAddresses() {
        final GetAddressesUseCaseOutput getAddressesUseCaseOutput = this.getAddressesUseCase.execute();

        return ResponseEntity.ok().body(GetAddressesResponse.fromOutput(getAddressesUseCaseOutput));
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<Object> getAddress(@PathVariable String addressId) {
        final GetAddressUseCaseOutput getAddressUseCaseOutput = this.getAddressUseCase.execute(new GetAddressUseCaseInput(addressId));

        return ResponseEntity.ok().body(GetAddressResponse.fromOutput(getAddressUseCaseOutput));
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Object> deleteAddress(@PathVariable String addressId) {
        this.deleteAddressUseCase.execute(new DeleteAddressUseCaseInput(addressId));

        return ResponseEntity.ok().body(new MessageResponse("Address deleted successfully"));
    }
}
