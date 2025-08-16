package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.CreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateVerificationCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;

import java.util.Optional;

public class CreateVerificationCodeUseCaseImpl implements CreateVerificationCodeUseCase {
    private final UserCodeRepository userCodeRepository;
    private final CodeProvider codeProvider;

    public CreateVerificationCodeUseCaseImpl(
            UserCodeRepository userCodeRepository,
            CodeProvider codeProvider
    ) {
        this.userCodeRepository = userCodeRepository;
        this.codeProvider = codeProvider;
    }

    @Override
    public CreateVerificationCodeUseCaseOutput execute(CreateVerificationCodeUseCaseInput createVerificationCodeUseCaseInput) {
        this.getPreviousUserCodeByUserIdAndCodeType(createVerificationCodeUseCaseInput.userId()).ifPresent(userCode ->
                this.deleteUserCodeById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(createVerificationCodeUseCaseInput.userId(), codeGenerated, UserCodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        return CreateVerificationCodeUseCaseOutput.from(savedUserCode);
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String userId) {
        return this.userCodeRepository.findByUserIdAndCodeType(userId, UserCodeType.USER_VERIFICATION.name());
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }
}