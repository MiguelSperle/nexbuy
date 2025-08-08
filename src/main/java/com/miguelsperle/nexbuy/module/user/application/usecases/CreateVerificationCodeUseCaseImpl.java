package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateVerificationCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;

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
        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(createVerificationCodeUseCaseInput.userId(), codeGenerated, UserCodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        return CreateVerificationCodeUseCaseOutput.from(savedUserCode);
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }
}