package com.example.AgeCare.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Pega o usuário logado (email, ID, etc) — se tiver Spring Security
        return Optional.of("sistema@agecare.com"); // ou retornar do SecurityContextHolder
    }
}
