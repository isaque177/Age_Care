package com.example.AgeCare.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    // Se não tiver login ativo, retorne um valor fixo:
    return Optional.of("system");
    // Depois, quando ativar Security, retorne o usuário logado:
    // return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
  }
}
