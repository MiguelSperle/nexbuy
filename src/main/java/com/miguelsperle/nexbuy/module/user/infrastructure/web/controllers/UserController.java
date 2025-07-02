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
    private final IResendUserVerificationCodeUseCase resendUserVerificationCodeUseCase;
    private final IUpdateUserToVerifiedUseCase updateUserToVerifiedUseCase;
    private final IRefreshTokenUseCase refreshTokenUseCase;
    private final ICreateUserPasswordResetCodeUseCase createUserPasswordResetCodeUseCase;
    private final IValidateUserPasswordResetCodeUseCase validateUserPasswordResetCodeUseCase;
    private final IResetUserPasswordUseCase resetUserPasswordUseCase;
    private final IUpdateUserInformationUseCase updateUserInformationUseCase;
    private final IUpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final IGetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final ICreateAddressUseCase createAddressUseCase;
    private final IUpdateAddressUseCase updateAddressUseCase;
    private final IGetAddressesUseCase getAddressesUseCase;
    private final IDeleteAddressUseCase deleteAddressUseCase;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        PersonComplementInput personComplementInput = null;

        final PersonType convertedToPersonType = PersonType.valueOf(createUserRequest.getPersonType());

        if (convertedToPersonType == PersonType.NATURAL_PERSON && createUserRequest.getNaturalPersonComplement() != null) {
            personComplementInput = new PersonComplementInput(
                    createUserRequest.getNaturalPersonComplement().getCpf(),
                    createUserRequest.getNaturalPersonComplement().getGeneralRegister(),
                    null, null, null, null
            );
        } else if (convertedToPersonType == PersonType.LEGAL_PERSON && createUserRequest.getLegalPersonComplement() != null) {
            personComplementInput = new PersonComplementInput(
                    null, null,
                    createUserRequest.getLegalPersonComplement().getCnpj(),
                    createUserRequest.getLegalPersonComplement().getFantasyName(),
                    createUserRequest.getLegalPersonComplement().getLegalName(),
                    createUserRequest.getLegalPersonComplement().getStateRegistration()
            );
        }

        this.createUserUseCase.execute(new CreateUserUseCaseInput(
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail(),
                createUserRequest.getPassword(),
                createUserRequest.getPhoneNumber(),
                convertedToPersonType,
                personComplementInput
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User created successfully"));
    }

    @PostMapping("/authentication")
    public ResponseEntity<Object> authenticate(@RequestBody @Valid AuthenticateRequest authenticateRequest) {
        final AuthenticateUseCaseOutput authenticateUseCaseOutput = this.authenticateUseCase.execute(new AuthenticateUseCaseInput(
                authenticateRequest.getEmail(), authenticateRequest.getPassword()
        ));

        return ResponseEntity.ok().body(AuthenticateResponse.fromOutput(authenticateUseCaseOutput));
    }

    @PostMapping("/verification-code")
    public ResponseEntity<Object> resendUserVerificationCode(@RequestBody @Valid ResendUserVerificationCodeRequest resendUserVerificationCodeRequest) {
        this.resendUserVerificationCodeUseCase.execute(new ResendUserVerificationCodeUseCaseInput(
                resendUserVerificationCodeRequest.getEmail()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User verification code sent successfully"));
    }

    @PatchMapping("/verification")
    public ResponseEntity<Object> updateUserToVerified(@RequestBody @Valid UpdateUserToVerifiedRequest updateUserToVerifiedRequest) {
        this.updateUserToVerifiedUseCase.execute(new UpdateUserToVerifiedUseCaseInput(
                updateUserToVerifiedRequest.getCode()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User verified successfully"));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<Object> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        final RefreshTokenUseCaseOutput refreshTokenUseCaseOutput = this.refreshTokenUseCase.execute(new RefreshTokenUseCaseInput(
                refreshTokenRequest.getRefreshToken()
        ));

        return ResponseEntity.ok().body(RefreshTokenResponse.fromOutput(refreshTokenUseCaseOutput));
    }

    @PostMapping("/password-recovery")
    public ResponseEntity<Object> createUserPasswordResetCode(@RequestBody @Valid CreateUserPasswordResetCodeRequest createUserPasswordResetCodeRequest) {
        this.createUserPasswordResetCodeUseCase.execute(new CreateUserPasswordResetCodeUseCaseInput(
                createUserPasswordResetCodeRequest.getEmail()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Password reset code sent successfully"));
    }

    @PostMapping("/password-recovery/validation")
    public ResponseEntity<Object> validateUserPasswordResetCode(@RequestBody @Valid ValidateUserPasswordResetCodeRequest validateUserPasswordResetCodeRequest) {
        final ValidateUserPasswordResetCodeUseCaseOutput validateUserPasswordResetCodeUseCaseOutput = this.validateUserPasswordResetCodeUseCase.execute(new ValidateUserPasswordResetCodeUseCaseInput(
                validateUserPasswordResetCodeRequest.getCode()
        ));

        return ResponseEntity.ok().body(ValidateUserPasswordResetCodeResponse.fromOutput(validateUserPasswordResetCodeUseCaseOutput));
    }

    @PatchMapping("/password-reset")
    public ResponseEntity<Object> resetUserPassword(@RequestBody @Valid ResetUserPasswordRequest resetUserPasswordRequest) {
        this.resetUserPasswordUseCase.execute(new ResetUserPasswordUseCaseInput(
                resetUserPasswordRequest.getCode(), resetUserPasswordRequest.getPassword()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User password reset successfully"));
    }

    @PatchMapping("/information")
    public ResponseEntity<Object> updateUserInformation(@RequestBody @Valid UpdateUserInformationRequest updateUserInformationRequest) {
        this.updateUserInformationUseCase.execute(new UpdateUserInformationUseCaseInput(
                updateUserInformationRequest.getFirstName(), updateUserInformationRequest.getLastName(),
                updateUserInformationRequest.getPhoneNumber()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User information updated successfully"));
    }

    @PatchMapping("/password")
    public ResponseEntity<Object> updateUserPassword(@RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        this.updateUserPasswordUseCase.execute(new UpdateUserPasswordUseCaseInput(
                updateUserPasswordRequest.getCurrentPassword(), updateUserPasswordRequest.getPassword()
        ));

        return ResponseEntity.ok().body(new MessageResponse("User password updated successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getAuthenticatedUser() {
        final GetAuthenticatedUserUseCaseOutput getAuthenticatedUserUseCaseOutput = this.getAuthenticatedUserUseCase.execute();

        if (getAuthenticatedUserUseCaseOutput.getAuthenticatedUser().getPersonType() == PersonType.NATURAL_PERSON) {
            return ResponseEntity.ok().body(GetAuthenticatedUserNaturalPersonResponse.fromOutput(getAuthenticatedUserUseCaseOutput));
        } else {
            return ResponseEntity.ok().body(GetAuthenticatedUserLegalPersonResponse.fromOutput(getAuthenticatedUserUseCaseOutput));
        }
    }

    @PostMapping("/addresses")
    public ResponseEntity<Object> createAddress(@RequestBody @Valid CreateAddressRequest createAddressRequest) {
        this.createAddressUseCase.execute(new CreateAddressUseCaseInput(
                createAddressRequest.getAddressLine(),
                createAddressRequest.getAddressNumber(),
                createAddressRequest.getZipCode(),
                createAddressRequest.getNeighborhood(),
                createAddressRequest.getCity(),
                createAddressRequest.getUf(),
                createAddressRequest.getComplement()
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
                updateAddressRequest.getAddressLine(),
                updateAddressRequest.getAddressNumber(),
                updateAddressRequest.getZipCode(),
                updateAddressRequest.getNeighborhood(),
                updateAddressRequest.getCity(),
                updateAddressRequest.getUf(),
                updateAddressRequest.getComplement()
        ));

        return ResponseEntity.ok().body(new MessageResponse("Address updated successfully"));
    }

    @GetMapping("/addresses")
    public List<GetAddressesResponse> getAddresses() {
        final GetAddressesUseCaseOutput getAddressesUseCaseOutput = this.getAddressesUseCase.execute();

        return GetAddressesResponse.fromOutput(getAddressesUseCaseOutput);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Object> deleteAddress(@PathVariable String addressId) {
        this.deleteAddressUseCase.execute(new DeleteAddressUseCaseInput(addressId));

        return ResponseEntity.ok().body(new MessageResponse("Address deleted successfully"));
    }
}
