package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.RefreshTokenUseCaseOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {
    private String accessToken;

    public static RefreshTokenResponse fromOutput(RefreshTokenUseCaseOutput refreshTokenUseCaseOutput) {
        return new RefreshTokenResponse(refreshTokenUseCaseOutput.getAccessToken());
    }
}
