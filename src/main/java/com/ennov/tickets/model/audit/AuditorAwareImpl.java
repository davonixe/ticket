package com.ennov.tickets.model.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String>  {

    @SuppressWarnings("null")
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("David ONIDJE");
        // Use below commented code when will use Spring Security.
    }
}
