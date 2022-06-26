package com.example.Social_App.Service;

import com.example.Social_App.Model.ConfirmationToken;
import com.example.Social_App.Repositiry.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(final ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveToken(final ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);

    }

    public Optional<ConfirmationToken> getToken(final String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(final String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
